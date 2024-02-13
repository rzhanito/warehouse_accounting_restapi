package ru.rzhanito.uchet.sklad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rzhanito.uchet.sklad.response.Categories;

@Entity
@Table(name = "goods")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
