package ru.rzhanito.uchet.sklad.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rzhanito.uchet.sklad.entity.WarehouseEntity;

import java.util.List;
import java.util.stream.Collectors;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseResponse {
    private String name;
    private String location;
    private Integer capacity;
    private List<GoodsResponse> goods;

    public static WarehouseResponse toModel(WarehouseEntity entity){
        List<GoodsResponse> listOfGoods = entity.getGoods().stream().map(GoodsResponse::toModel).toList();
        return new WarehouseResponse(entity.getName(), entity.getLocation(), entity.getCapacity(), listOfGoods);
    }
}
