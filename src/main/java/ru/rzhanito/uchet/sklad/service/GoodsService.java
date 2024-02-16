package ru.rzhanito.uchet.sklad.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rzhanito.uchet.sklad.entity.GoodsEntity;
import ru.rzhanito.uchet.sklad.entity.WarehouseEntity;
import ru.rzhanito.uchet.sklad.exception.EntityAlreadyExistsException;
import ru.rzhanito.uchet.sklad.exception.EntityNotFoundException;
import ru.rzhanito.uchet.sklad.exception.GoodsCannotFitIntoWarehouseException;
import ru.rzhanito.uchet.sklad.repo.GoodsRepo;
import ru.rzhanito.uchet.sklad.repo.WarehouseRepo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GoodsService {
    private final GoodsRepo goodsRepo;
    private final WarehouseRepo warehouseRepo;
    private final GoodsEventService goodsEventService;

    public String addGoods(GoodsEntity newGoods) throws EntityNotFoundException, GoodsCannotFitIntoWarehouseException {
        Optional<WarehouseEntity> existingWarehouse = warehouseRepo.findByName(newGoods.getWarehouse().getName());
        if (existingWarehouse.isPresent()) {
            WarehouseEntity foundWarehouse = existingWarehouse.get();

            List<GoodsEntity> existingGoodsList = foundWarehouse.getGoods();
            Optional<GoodsEntity> foundGoodsInWarehouse = Optional.empty();

            if (existingGoodsList != null) {
                foundGoodsInWarehouse = existingGoodsList.stream()
                        .filter(goods -> goods.getName().equals(newGoods.getName()))
                        .findFirst();
            }


            if (foundWarehouse.getCapacity() - newGoods.getAmount() >= 0) {

                if (foundGoodsInWarehouse.isPresent()) {
                    GoodsEntity foundGoods = foundGoodsInWarehouse.get();
                    foundWarehouse.setCapacity(foundWarehouse.getCapacity() - newGoods.getAmount());
                    foundGoods.setWeight(foundGoods.getWeight() + newGoods.getWeight());
                    foundGoods.setAmount(foundGoods.getAmount() + newGoods.getAmount());
                    goodsRepo.save(foundGoods);
                    warehouseRepo.save(foundWarehouse);
                    goodsEventService.addingGoodsEvent(foundGoods);
                    return "Такой товар уже есть на этом складе. Увеличены количество и вес товара.";
                } else {
                    newGoods.setWarehouse(foundWarehouse);
                    foundWarehouse.getGoods().add(newGoods);
                    foundWarehouse.setCapacity(foundWarehouse.getCapacity() - newGoods.getAmount());
                    goodsRepo.save(newGoods);
                    warehouseRepo.save(foundWarehouse);
                    goodsEventService.addingGoodsEvent(newGoods);
                    return "Товар успешно добавлен.";
                }
            } else {
                throw new GoodsCannotFitIntoWarehouseException("Такое количество товара не поместится на склад " + foundWarehouse.getName() + ". Максимум товара, которое можно туда поместить - " + foundWarehouse.getCapacity());
            }
        } else {
            throw new EntityNotFoundException("Склад " + newGoods.getWarehouse().getName() + " не найден.");
        }
    }

    public void changeWarehouse(String goodsName, String anotherWarehouseName) throws EntityAlreadyExistsException, EntityNotFoundException {
        Optional<GoodsEntity> existingGoods = goodsRepo.findByName(goodsName);
        if (existingGoods.isPresent()) {
            Optional<WarehouseEntity> existingWarehouse = warehouseRepo.findByName(anotherWarehouseName);
            if (existingWarehouse.isPresent()) {
                existingGoods.get().setWarehouse(existingWarehouse.get());
                goodsRepo.save(existingGoods.get());
            } else {
                throw new EntityNotFoundException("Склад " + anotherWarehouseName + " не найден.");
            }
        } else {
            throw new EntityAlreadyExistsException("Товар " + goodsName + " не найден.");
        }
    }

    public void deleteGoods(String goodsToDeleteName, String warehouseName) throws EntityNotFoundException {
        Optional<WarehouseEntity> existingWarehouse = warehouseRepo.findByName(warehouseName);
        if (existingWarehouse.isPresent()) {
            WarehouseEntity foundWarehouse = existingWarehouse.get();
            Optional<GoodsEntity> foundGoods = foundWarehouse.getGoods()
                    .stream().filter(goods -> goods.getName().equals(goodsToDeleteName))
                    .findAny();
            if (foundGoods.isPresent()) {
                GoodsEntity goodsToDelete = foundGoods.get();
                foundWarehouse.getGoods().remove(goodsToDelete);
                foundWarehouse.setCapacity(foundWarehouse.getCapacity() + goodsToDelete.getAmount());
                goodsRepo.delete(goodsToDelete);
                warehouseRepo.save(foundWarehouse);
            } else {
                throw new EntityNotFoundException("На складе " + warehouseName + " нет товара " + goodsToDeleteName);
            }
        } else {
            throw new EntityNotFoundException("Склад " + warehouseName + " не найден.");
        }
    }

    public String getGoodsLocation(String goodsName) throws EntityAlreadyExistsException {
        Optional<GoodsEntity> existingGoods = goodsRepo.findByName(goodsName);
        if (existingGoods.isPresent()) {
            return existingGoods.get().getWarehouse().getName();
        } else {
            throw new EntityAlreadyExistsException("Товар " + goodsName + " не найден.");
        }
    }
}
