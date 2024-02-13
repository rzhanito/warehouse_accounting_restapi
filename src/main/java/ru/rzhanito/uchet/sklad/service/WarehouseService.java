package ru.rzhanito.uchet.sklad.service;

import org.springframework.stereotype.Service;
import ru.rzhanito.uchet.sklad.entity.WarehouseEntity;
import ru.rzhanito.uchet.sklad.exception.EntityAlreadyExistsException;
import ru.rzhanito.uchet.sklad.exception.EntityNotFoundException;
import ru.rzhanito.uchet.sklad.repo.WarehouseRepo;
import ru.rzhanito.uchet.sklad.response.WarehouseResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    private final WarehouseRepo warehouseRepo;

    public WarehouseService(WarehouseRepo warehouseRepo) {
        this.warehouseRepo = warehouseRepo;
    }

    public void createWarehouse(WarehouseEntity warehouse) throws EntityAlreadyExistsException {
        if (warehouseRepo.findByName(warehouse.getName()).isEmpty()) {
            warehouseRepo.save(warehouse);
        } else
            throw new EntityAlreadyExistsException("Склад с таким названием уже существует.");
    }

    public WarehouseResponse deleteWarehouse(String warehouseName) throws EntityNotFoundException {
        Optional<WarehouseEntity> warehouse = warehouseRepo.findByName(warehouseName);
        if (warehouse.isPresent()) {
            warehouseRepo.delete(warehouse.get());
            return WarehouseResponse.toModel(warehouse.get());
        } else {
            throw new EntityNotFoundException("Склада с названием: " + warehouseName + " не существует.");
        }
    }

    public List<WarehouseResponse> getAllWarehouses() throws EntityNotFoundException {
        List<WarehouseEntity> warehouses = warehouseRepo.findAll();
        if(warehouses.isEmpty()){
            throw new EntityNotFoundException("Складов нет.");
        } else{
            return warehouses.stream().map(WarehouseResponse::toModel).collect(Collectors.toList());
        }
    }

    public WarehouseResponse getWarehouseByName(String name) throws EntityNotFoundException {
        Optional<WarehouseEntity> warehouse = warehouseRepo.findByName(name);
        if(warehouse.isPresent()){
            return WarehouseResponse.toModel(warehouse.get());
        }else {
            throw new EntityNotFoundException("Склад " + name + " не найден.");
        }
    }

    public WarehouseResponse editWarehousePartially(String name, String newName, String location, Integer capacity) throws EntityNotFoundException {
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
            return WarehouseResponse.toModel(warehouse.get());
        } else{
            throw new EntityNotFoundException("Склад " + name + " не найден.");
        }

    }
}
