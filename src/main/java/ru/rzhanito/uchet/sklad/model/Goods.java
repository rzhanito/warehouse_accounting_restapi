package ru.rzhanito.uchet.sklad.model;

import ru.rzhanito.uchet.sklad.entity.GoodsEntity;

public class Goods {
    private String name;
    private Double weight;
    private Categories category;
    private String warehouseName;

    public Goods() {
    }
    public static Goods toModel(GoodsEntity entity){
        Goods model = new Goods();
        model.setName(entity.getName());
        model.setWeight(entity.getWeight());
        model.setCategory(entity.getCategory());
        model.setWarehouseName(entity.getWarehouse().getName());

        return model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
