package ru.rzhanito.uchet.sklad.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rzhanito.uchet.sklad.entity.WarehouseEntity;
import ru.rzhanito.uchet.sklad.exception.WarehouseAlreadyExistsException;
import ru.rzhanito.uchet.sklad.exception.WarehouseNotFoundException;
import ru.rzhanito.uchet.sklad.model.Warehouse;
import ru.rzhanito.uchet.sklad.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping("create")
    public ResponseEntity<String> createWarehouse(@RequestBody @Valid WarehouseEntity warehouse) {
        try {
            warehouseService.createWarehouse(warehouse);
            return ResponseEntity.ok().body("Склад успешно создан.");
        } catch (WarehouseAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Необработанная ошибка.");
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteWarehouse(@RequestParam String warehouseName) {
        try {
            return ResponseEntity.ok().body(warehouseService.deleteWarehouse(warehouseName));
        } catch (WarehouseNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Необработанная ошибка.");
        }
    }

    @GetMapping("get/all")
    public ResponseEntity<Object> getAllWarehouses(){
        try {
            List<Warehouse> warehouses = warehouseService.getAllWarehouses();
            return ResponseEntity.ok().body(warehouses);
        }catch (WarehouseNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Необработанная ошибка.");
        }
    }

    @GetMapping("get")
    public ResponseEntity<Object> getWarehouseByName(@RequestParam String name){
        try {
            return ResponseEntity.ok().body(warehouseService.getWarehouseByName(name));
        }catch (WarehouseNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Необработанная ошибка.");
        }
    }

    @PatchMapping("edit/{name}")
    public ResponseEntity<Object> editWarehousePartially
            (@PathVariable String name,
             @RequestParam(required = false) String newName,
             @RequestParam(required = false) String location,
             @RequestParam(required = false) Integer capacity)
    {
        try {
            return ResponseEntity.ok().body(warehouseService.editWarehousePartially(name, newName, location, capacity));
        }catch (WarehouseNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Необработанная ошибка.");
        }

    }
}
