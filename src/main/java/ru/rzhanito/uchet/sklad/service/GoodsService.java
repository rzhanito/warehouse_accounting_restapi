package ru.rzhanito.uchet.sklad.service;

import org.springframework.stereotype.Service;
import ru.rzhanito.uchet.sklad.entity.GoodsEntity;
import ru.rzhanito.uchet.sklad.entity.WarehouseEntity;
import ru.rzhanito.uchet.sklad.exception.EntityAlreadyExistsException;
import ru.rzhanito.uchet.sklad.exception.EntityNotFoundException;
import ru.rzhanito.uchet.sklad.repo.GoodsRepo;
import ru.rzhanito.uchet.sklad.repo.WarehouseRepo;

import java.util.Optional;

@Service
public class GoodsService {
    private final GoodsRepo goodsRepo;
    private final WarehouseRepo warehouseRepo;

    public GoodsService(GoodsRepo goodsRepo, WarehouseRepo warehouseRepo) {
        this.goodsRepo = goodsRepo;
        this.warehouseRepo = warehouseRepo;
    }

    public String addGoods(GoodsEntity goodsEntity) throws EntityNotFoundException {
        Optional<GoodsEntity> existingGoods = goodsRepo.findByName(goodsEntity.getName());
        if(existingGoods.isPresent()){
            existingGoods.get().setWeight(existingGoods.get().getWeight() + goodsEntity.getWeight());
            goodsRepo.save(existingGoods.get());
            return "Такой товар уже существует. Увлеичен вес товара.";
        }else {
            Optional<WarehouseEntity> existingWarehouse = warehouseRepo.findByName(goodsEntity.getWarehouse().getName());
            if(existingWarehouse.isPresent()){
                goodsEntity.setWarehouse(existingWarehouse.get());
                existingWarehouse.get().getGoods().add(goodsEntity);
                goodsRepo.save(goodsEntity);
                return "Товар успешно добавлен.";
            } else{
                throw new EntityNotFoundException("Склад не найден.");
            }
        }
    }

    public void changeWarehouse(String goodsName, String anotherWarehouseName) throws EntityAlreadyExistsException, EntityNotFoundException {
        Optional<GoodsEntity> existingGoods = goodsRepo.findByName(goodsName);
        if(existingGoods.isPresent()){
            Optional<WarehouseEntity> existingWarehouse = warehouseRepo.findByName(anotherWarehouseName);
            if(existingWarehouse.isPresent()){
                existingGoods.get().setWarehouse(existingWarehouse.get());
                goodsRepo.save(existingGoods.get());
            }else {
                throw new EntityNotFoundException("Склад " + anotherWarehouseName + " не найден.");
            }
        }else{
            throw new EntityAlreadyExistsException("Товар " + goodsName + " не найден.");
        }
    }

    public void deleteGoods(String goodsToDeleteName) throws EntityAlreadyExistsException{
        Optional<GoodsEntity> existingGoods = goodsRepo.findByName(goodsToDeleteName);
        if(existingGoods.isPresent()){
            goodsRepo.delete(existingGoods.get());
        }else{
            throw new EntityAlreadyExistsException("Товар " + goodsToDeleteName + " не найден.");
        }
    }

    public String getGoodsLocation(String goodsName) throws EntityAlreadyExistsException {
        Optional<GoodsEntity> existingGoods = goodsRepo.findByName(goodsName);
        if(existingGoods.isPresent()){
            return existingGoods.get().getWarehouse().getName();
        }else{
            throw new EntityAlreadyExistsException("Товар " + goodsName + " не найден.");
        }
    }
}
