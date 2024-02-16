package ru.rzhanito.uchet.sklad.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rzhanito.uchet.sklad.entity.GoodsEventEntity;
import ru.rzhanito.uchet.sklad.service.GoodsEventService;

import java.util.List;

@RestController
@RequestMapping("event")
@Data
@AllArgsConstructor
public class GoodsEventController {
    private final GoodsEventService goodsEventService;

    @GetMapping("get_all")
    public ResponseEntity<List<GoodsEventEntity>> getAllEvents(){
        return ResponseEntity.ok(goodsEventService.getAllEvents());
    }
}
