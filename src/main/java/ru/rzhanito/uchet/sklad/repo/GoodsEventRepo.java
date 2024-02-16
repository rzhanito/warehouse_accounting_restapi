package ru.rzhanito.uchet.sklad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rzhanito.uchet.sklad.entity.GoodsEventEntity;

import java.util.List;

@Repository
public interface GoodsEventRepo extends JpaRepository<GoodsEventEntity, Long> {
    List<GoodsEventEntity> findAll();
}
