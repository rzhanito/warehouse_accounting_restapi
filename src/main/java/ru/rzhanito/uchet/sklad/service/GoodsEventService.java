package ru.rzhanito.uchet.sklad.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.rzhanito.uchet.sklad.entity.GoodsEntity;
import ru.rzhanito.uchet.sklad.entity.GoodsEventEntity;
import ru.rzhanito.uchet.sklad.enum_categories.GoodsEventCategory;
import ru.rzhanito.uchet.sklad.repo.GoodsEventRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Data
public class GoodsEventService {
    private final GoodsEventRepo goodsEventRepo;

    public void addingGoodsEvent(GoodsEntity goodsEntity){
        GoodsEventEntity event = new GoodsEventEntity();
        event.setNameBefore("");
        event.setAmountBefore(0L);
        event.setWeightBefore(0D);
        event.setWarehouseBefore("");

        event.setGoodEntity(goodsEntity);
        event.setNameAfter(goodsEntity.getName());
        event.setAmountAfter(goodsEntity.getAmount());
        event.setWeightAfter(goodsEntity.getWeight());
        event.setEventCategory(GoodsEventCategory.ADD);
        event.setWarehouseAfter(goodsEntity.getWarehouse().getName());
        event.setTimestamp(LocalDateTime.now());
        goodsEventRepo.save(event);
    }

    public void changingGoodsEvent(GoodsEntity oldGoodsEntity, GoodsEntity newGoodsEntity){
        GoodsEventEntity event = new GoodsEventEntity();

        // before
        event.setNameBefore(oldGoodsEntity.getName());
        event.setAmountBefore(oldGoodsEntity.getAmount());
        event.setWeightBefore(oldGoodsEntity.getWeight());
        event.setWarehouseBefore(oldGoodsEntity.getWarehouse().getName());

        // after
        event.setNameAfter(newGoodsEntity.getName());
        event.setAmountAfter(newGoodsEntity.getAmount());
        event.setWeightAfter(newGoodsEntity.getWeight());
        event.setWarehouseAfter(newGoodsEntity.getWarehouse().getName());

        event.setGoodEntity(newGoodsEntity);
        event.setEventCategory(GoodsEventCategory.EDIT);
        event.setTimestamp(LocalDateTime.now());

        goodsEventRepo.save(event);
    }

    public List<GoodsEventEntity> getAllEvents(){
        return goodsEventRepo.findAll();
    }


//    public List<GoodsEventEntity> getAllEventsByGoodsAndWarehouseName(String goodsName, String warehouseName){
//
//    }
}
