package ru.rzhanito.uchet.sklad.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.rzhanito.uchet.sklad.entity.GoodsEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsResponse {
    private String name;
    private Double weight;
    private Categories category;
    private String warehouseName;

    public static GoodsResponse toModel(GoodsEntity entity){
        return new GoodsResponse(entity.getName(), entity.getWeight(), entity.getCategory(), entity.getWarehouse().getName());
    }
}
