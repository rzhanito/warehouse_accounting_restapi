package ru.rzhanito.uchet.sklad.service;

import org.springframework.stereotype.Service;
import ru.rzhanito.uchet.sklad.entity.GoodsEntity;
import ru.rzhanito.uchet.sklad.entity.WarehouseEntity;
import ru.rzhanito.uchet.sklad.exception.GoodsNotFoundException;
import ru.rzhanito.uchet.sklad.exception.WarehouseNotFoundException;
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

    public boolean addGoods(GoodsEntity goodsEntity) throws WarehouseNotFoundException{
        Optional<GoodsEntity> existingGoods = goodsRepo.findByName(goodsEntity.getName());
        if(existingGoods.isPresent()){
            existingGoods.get().setWeight(existingGoods.get().getWeight() + goodsEntity.getWeight());
            goodsRepo.save(existingGoods.get());
            return true;
        }else {
            Optional<WarehouseEntity> existingWarehouse = warehouseRepo.findByName(goodsEntity.getWarehouse().getName());
            if(existingWarehouse.isPresent()){
                goodsEntity.setWarehouse(existingWarehouse.get());
                existingWarehouse.get().getGoods().add(goodsEntity);
                goodsRepo.save(goodsEntity);
                return false;
            } else{
                throw new WarehouseNotFoundException("Склад не найден.");
            }
        }
    }

    public void changeWarehouse(String goodsName, String anotherWarehouseName) throws GoodsNotFoundException, WarehouseNotFoundException {
        Optional<GoodsEntity> existingGoods = goodsRepo.findByName(goodsName);
        if(existingGoods.isPresent()){
            Optional<WarehouseEntity> existingWarehouse = warehouseRepo.findByName(anotherWarehouseName);
            if(existingWarehouse.isPresent()){
                existingGoods.get().setWarehouse(existingWarehouse.get());
                goodsRepo.save(existingGoods.get());
            }else {
                throw new WarehouseNotFoundException("Склад " + anotherWarehouseName + " не найден.");
            }
        }else{
            throw new GoodsNotFoundException("Товар " + goodsName + " не найден.");
        }
    }

    public void deleteGoods(String goodsToDeleteName) throws GoodsNotFoundException{
        Optional<GoodsEntity> existingGoods = goodsRepo.findByName(goodsToDeleteName);
        if(existingGoods.isPresent()){
            goodsRepo.delete(existingGoods.get());
        }else{
            throw new GoodsNotFoundException("Товар " + goodsToDeleteName + " не найден.");
        }
    }

    public String getGoodsLocation(String goodsName) throws GoodsNotFoundException {
        Optional<GoodsEntity> existingGoods = goodsRepo.findByName(goodsName);
        if(existingGoods.isPresent()){
            return existingGoods.get().getWarehouse().getName();
        }else{
            throw new GoodsNotFoundException("Товар " + goodsName + " не найден.");
        }
    }
}
