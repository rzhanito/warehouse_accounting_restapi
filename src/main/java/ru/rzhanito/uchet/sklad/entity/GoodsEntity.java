package ru.rzhanito.uchet.sklad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import ru.rzhanito.uchet.sklad.enum_categories.GoodsCategory;

@Entity
@Table(name = "goods")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE goods SET deleted = true WHERE id = ?")
@SQLRestriction("deleted <> true")
public class GoodsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Имя товара не может быть null")
    private String name;
    @NotNull(message = "Количество товара не может быть null")
    @Min(value = 1, message = "Количество товара не может быть < 1")
    private Long amount;
    @NotNull(message = "Вес не может быть null")
    private Double weight;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Категория не может быть null")
    private GoodsCategory category;
    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull(message = "Склад не может быть null. Товар должен обязательно находиться на складе")
    private WarehouseEntity warehouse;

    private boolean deleted = false;
}
