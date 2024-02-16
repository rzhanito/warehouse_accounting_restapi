package ru.rzhanito.uchet.sklad.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rzhanito.uchet.sklad.entity.WarehouseEntity;
import ru.rzhanito.uchet.sklad.exception.EntityAlreadyExistsException;
import ru.rzhanito.uchet.sklad.exception.EntityNotFoundException;
import ru.rzhanito.uchet.sklad.response.WarehouseResponse;
import ru.rzhanito.uchet.sklad.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("warehouse")
@AllArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping("create")
    public ResponseEntity<String> createWarehouse(@RequestBody @Valid WarehouseEntity warehouse) throws EntityAlreadyExistsException {
        warehouseService.createWarehouse(warehouse);
        return ResponseEntity.ok().body("Склад " + warehouse.getName() + " успешно создан.");
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteWarehouse(@RequestParam String warehouseName) throws EntityNotFoundException {
        return ResponseEntity.ok().body(warehouseService.deleteWarehouse(warehouseName));
    }

    @GetMapping("get_all")
    public ResponseEntity<Object> getAllWarehouses() throws EntityNotFoundException {
        List<WarehouseResponse> warehouses = warehouseService.getAllWarehouses();
        return ResponseEntity.ok().body(warehouses);
    }

    @GetMapping("get")
    public ResponseEntity<Object> getWarehouseByName(@RequestParam String name) throws EntityNotFoundException {
        return ResponseEntity.ok().body(warehouseService.getWarehouseByName(name));
    }

    @PatchMapping("edit/{name}")
    public ResponseEntity<Object> editWarehousePartially
            (@PathVariable String name,
             @RequestParam(required = false) String newName,
             @RequestParam(required = false) String location,
             @RequestParam(required = false) Long capacity) throws EntityNotFoundException {
        return ResponseEntity.ok().body(warehouseService.editWarehousePartially(name, newName, location, capacity));

    }
}
