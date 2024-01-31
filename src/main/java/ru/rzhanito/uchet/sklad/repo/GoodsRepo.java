package ru.rzhanito.uchet.sklad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rzhanito.uchet.sklad.entity.GoodsEntity;

import java.util.Optional;

@Repository
public interface GoodsRepo extends JpaRepository<GoodsEntity, Long> {
    Optional<GoodsEntity> findByName(String name);
}
