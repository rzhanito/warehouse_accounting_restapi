package ru.rzhanito.uchet.sklad.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rzhanito.uchet.sklad.enum_categories.GoodsEventCategory;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsEventResponse {
    private Long id;
    private String goodsName;
    private LocalDateTime timestamp;
    private GoodsEventCategory eventCategory;
    private String nameBefore;
    private Long amountBefore;
    private Double weightBefore;
    private String warehouseBefore;


    private String nameAfter;
    private Long amountAfter;
    private Double weightAfter;
    private String warehouseAfter;

    private boolean deleted = false;


}
