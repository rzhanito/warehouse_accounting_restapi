package ru.rzhanito.uchet.sklad.model;

import ru.rzhanito.uchet.sklad.entity.GoodsEntity;
import ru.rzhanito.uchet.sklad.entity.WarehouseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class Warehouse {
    private String name;
    private String location;
    private Integer capacity;
    private List<Goods> goods;

    public Warehouse() {
    }

    public static Warehouse toModel(WarehouseEntity entity){
        Warehouse model = new Warehouse();
        model.setName(entity.getName());
        model.setLocation(entity.getLocation());
        model.setCapacity(entity.getCapacity());
        model.setGoods(entity.getGoods().stream().map(Goods::toModel).collect(Collectors.toList()));
        return model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }
}
