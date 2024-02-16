package ru.rzhanito.uchet.sklad.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rzhanito.uchet.sklad.entity.GoodsEntity;
import ru.rzhanito.uchet.sklad.enum_categories.GoodsCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsResponse {
    private String name;
    private Long amount;
    private Double weight;
    private GoodsCategory category;
    private String warehouseName;

    public static GoodsResponse toModel(GoodsEntity entity){
        return new GoodsResponse(entity.getName(), entity.getAmount(), entity.getWeight(), entity.getCategory(), entity.getWarehouse().getName());
    }
}
