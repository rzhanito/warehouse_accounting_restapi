package ru.rzhanito.uchet.sklad.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rzhanito.uchet.sklad.entity.WarehouseEntity;
import ru.rzhanito.uchet.sklad.model.Warehouse;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepo extends JpaRepository<WarehouseEntity, Long> {
    Optional<WarehouseEntity> findByName(String name);
    WarehouseEntity deleteByName(String name);
    List<WarehouseEntity> findAll();
}
