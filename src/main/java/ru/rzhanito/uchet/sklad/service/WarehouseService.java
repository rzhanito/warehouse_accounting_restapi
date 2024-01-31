package ru.rzhanito.uchet.sklad.service;

import org.springframework.stereotype.Service;
import ru.rzhanito.uchet.sklad.entity.WarehouseEntity;
import ru.rzhanito.uchet.sklad.exception.WarehouseAlreadyExistsException;
import ru.rzhanito.uchet.sklad.exception.WarehouseNotFoundException;
import ru.rzhanito.uchet.sklad.model.Warehouse;
import ru.rzhanito.uchet.sklad.repo.WarehouseRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    private final WarehouseRepo warehouseRepo;

    public WarehouseService(WarehouseRepo warehouseRepo) {
        this.warehouseRepo = warehouseRepo;
    }

    public void createWarehouse(WarehouseEntity warehouse) throws WarehouseAlreadyExistsException {
        if (warehouseRepo.findByName(warehouse.getName()).isEmpty()) {
            warehouseRepo.save(warehouse);
        } else
            throw new WarehouseAlreadyExistsException("Склад с таким названием уже существует.");
    }

    public Warehouse deleteWarehouse(String warehouseName) throws WarehouseNotFoundException {
        Optional<WarehouseEntity> warehouse = warehouseRepo.findByName(warehouseName);
        if (warehouse.isPresent()) {
            warehouseRepo.delete(warehouse.get());
            return Warehouse.toModel(warehouse.get());
        } else {
            throw new WarehouseNotFoundException("Склада с названием: " + warehouseName + " не существует.");
        }
    }

    public List<Warehouse> getAllWarehouses() throws WarehouseNotFoundException{
        List<WarehouseEntity> warehouses = warehouseRepo.findAll();
        if(warehouses.isEmpty()){
            throw new WarehouseNotFoundException("Складов нет.");
        } else{
            return warehouses.stream().map(Warehouse::toModel).collect(Collectors.toList());
        }
    }

    public Warehouse getWarehouseByName(String name) throws WarehouseNotFoundException{
        Optional<WarehouseEntity> warehouse = warehouseRepo.findByName(name);
        if(warehouse.isPresent()){
            return Warehouse.toModel(warehouse.get());
        }else {
            throw new WarehouseNotFoundException("Склад " + name + " не найден.");
        }
    }

    public Warehouse editWarehousePartially(String name, String newName, String location, Integer capacity) throws WarehouseNotFoundException{
        Optional<WarehouseEntity> warehouse = warehouseRepo.findByName(name);
        if(warehouse.isPresent()){
            if(newName != null){
                warehouse.get().setName(newName);
            }
            if(location != null){
                warehouse.get().setLocation(location);
            }
            if(capacity != null){
                warehouse.get().setCapacity(capacity);
            }
            warehouseRepo.save(warehouse.get());
            return Warehouse.toModel(warehouse.get());
        } else{
            throw new WarehouseNotFoundException("Склад " + name + " не найден.");
        }

    }
}
