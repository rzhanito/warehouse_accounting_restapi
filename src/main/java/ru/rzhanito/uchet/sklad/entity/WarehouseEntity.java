package ru.rzhanito.uchet.sklad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity(name = "warehouse")
public class WarehouseEntity {
    public WarehouseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не может быть null")
    private String name;
    @NotBlank(message = "Локация не может быть null")
    private String location;
    @NotNull(message = "Вместимость не может быть null")
    @Max(value = 500000, message = "Максимальная вместимость - 500000")
    @Min(value = 10000, message = "Минимальная вместимость - 10000")
    private Integer capacity;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "warehouse")
    private List<GoodsEntity> goods;


    public WarehouseEntity(Long id, String name, String location, Integer capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
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

    public List<GoodsEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsEntity> goods) {
        this.goods = goods;
    }
}
