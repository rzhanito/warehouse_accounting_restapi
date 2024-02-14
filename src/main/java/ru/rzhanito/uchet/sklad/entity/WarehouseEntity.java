package ru.rzhanito.uchet.sklad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "warehouses")
@AllArgsConstructor
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE warehouses SET deleted = true WHERE id = ?")
@SQLRestriction("deleted <> true")
public class WarehouseEntity {

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

    private boolean deleted = false;

}
