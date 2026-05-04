package com.cornerstone.repository;

import com.cornerstone.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnitJpaRepository extends JpaRepository<UnitEntity, Long> {

    Optional<UnitEntity> findByUnitNumber(String unitNumber);

    // AGREGADO: solo trae unidades que NO están archivadas
    List<UnitEntity> findByArchivedFalse();

    // AGREGADO: busca por unitNumber solo entre las no archivadas
    Optional<UnitEntity> findByUnitNumberAndArchivedFalse(String unitNumber);
}