package ru.rzhanito.uchet.sklad.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rzhanito.uchet.sklad.entity.GoodsEntity;
import ru.rzhanito.uchet.sklad.exception.EntityAlreadyExistsException;
import ru.rzhanito.uchet.sklad.exception.EntityNotFoundException;
import ru.rzhanito.uchet.sklad.exception.GoodsCannotFitIntoWarehouseException;
import ru.rzhanito.uchet.sklad.service.GoodsService;

@RestController
@RequestMapping("goods")
@AllArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    @PostMapping("add")
    public ResponseEntity<String> addGoods(@Valid @RequestBody GoodsEntity goodsEntity)
            throws EntityNotFoundException, GoodsCannotFitIntoWarehouseException {
        return ResponseEntity.ok(goodsService.addGoods(goodsEntity));
    }

    @PatchMapping("change_warehouse")
    public ResponseEntity<String> changeWarehouse(@RequestParam String goodsName, @RequestParam String anotherWarehouseName) throws EntityAlreadyExistsException, EntityNotFoundException {
        goodsService.changeWarehouse(goodsName, anotherWarehouseName);
        return ResponseEntity.ok().body("Товар " + goodsName + " перемещен на склад " + anotherWarehouseName);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteGoods(@RequestParam(value = "goodsName") String goodsToDeleteName, @RequestParam(value = "warehouseName") String warehouseName) throws EntityNotFoundException {
        goodsService.deleteGoods(goodsToDeleteName, warehouseName);
        return ResponseEntity.ok().body("Товар " + goodsToDeleteName + " удалён из базы данных.");
    }

    @GetMapping("find_goods_location")
    public ResponseEntity<String> getGoodsLocation(@RequestParam String goodsName) throws EntityAlreadyExistsException {
        return ResponseEntity.ok().body("Товар " + goodsName + " хранится на складе " + goodsService.getGoodsLocation(goodsName));
    }
}
