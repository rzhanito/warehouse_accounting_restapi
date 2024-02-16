package ru.rzhanito.uchet.sklad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import ru.rzhanito.uchet.sklad.enum_categories.GoodsEventCategory;

import java.time.LocalDateTime;

@Entity
@Table(name = "goods_events")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE goods_events SET deleted = true WHERE id = ?")
@SQLRestriction("deleted <> true")
public class GoodsEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "goods_id")
    @JsonIgnore
    private GoodsEntity goodEntity;
    private Long goodEntityId;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private GoodsEventCategory eventCategory;
    private String nameBefore;
    private Long amountBefore;
    private Double weightBefore;
    private String warehouseBefore;


    private String nameAfter;
    private Long amountAfter;
    private Double weightAfter;
    private String warehouseAfter;

    @JsonIgnore
    private boolean deleted = false;

    @PostLoad
    private void loadGoodEntityId() {
        if (this.goodEntity != null) {
            this.goodEntityId = this.goodEntity.getId();
        }
    }
}
