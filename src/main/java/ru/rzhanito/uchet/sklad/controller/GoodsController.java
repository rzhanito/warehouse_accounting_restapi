package ru.rzhanito.uchet.sklad.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rzhanito.uchet.sklad.entity.GoodsEntity;
import ru.rzhanito.uchet.sklad.exception.GoodsNotFoundException;
import ru.rzhanito.uchet.sklad.exception.WarehouseNotFoundException;
import ru.rzhanito.uchet.sklad.service.GoodsService;

@RestController
@RequestMapping("goods")
public class GoodsController {
    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping("add")
    public ResponseEntity<String> addGoods(@Valid @RequestBody GoodsEntity goodsEntity){
        try {
            boolean isFound = goodsService.addGoods(goodsEntity);
            if(!isFound) {
                return ResponseEntity.ok().body("Товар успешно добавлен.");
            } else{
                return ResponseEntity.ok().body("Такой товар уже существует. Увеличен вес товара.");
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Необработанная ошибка.");
        }
    }

    @PatchMapping("change_warehouse")
    public ResponseEntity<String> changeWarehouse(@RequestParam String goodsName,@RequestParam String anotherWarehouseName){
        try {
            goodsService.changeWarehouse(goodsName,anotherWarehouseName);
            return ResponseEntity.ok().body("Товар " + goodsName + " перемещен на склад " + anotherWarehouseName);
        }catch (WarehouseNotFoundException | GoodsNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Необработанная ошибка.");
        }
    }

    @DeleteMapping("delete/{name}")
    public ResponseEntity<String> deleteGoods(@PathVariable(value = "name") String goodsToDeleteName) {
        try {
            goodsService.deleteGoods(goodsToDeleteName);
            return ResponseEntity.ok().body("Товар " + goodsToDeleteName + " удалён из базы данных.");
        }catch (GoodsNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Необработанная ошибка.");
        }
    }

    @GetMapping("find_goods_location")
    public ResponseEntity<String> getGoodsLocation(@RequestParam String goodsName){
        try {
            return ResponseEntity.ok().body("Товар " + goodsName + " хранится на складе " + goodsService.getGoodsLocation(goodsName));
        }catch (GoodsNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Необработанная ошибка.");
        }
    }
}
