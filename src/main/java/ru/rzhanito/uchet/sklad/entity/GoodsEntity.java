package ru.rzhanito.uchet.sklad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.rzhanito.uchet.sklad.model.Categories;

@Entity(name = "goods")
public class GoodsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Имя товара не может быть null")
    private String name;
    @NotNull(message = "Вес не может быть null")
    private Double weight;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Категория не может быть null")
    private Categories category;
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull(message = "Склад не может быть null")
    private WarehouseEntity warehouse;

    public GoodsEntity() {
    }

    public GoodsEntity(Long id, String name, Double weight, Categories category, WarehouseEntity warehouse) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.category = category;
        this.warehouse = warehouse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }
}
