package com.cornerstone.repository;

import com.cornerstone.entity.UnitMaintenanceHistoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitMaintenanceHistoryJpaRepository extends JpaRepository<UnitMaintenanceHistoryEntity, Long> {

    List<UnitMaintenanceHistoryEntity> findByUnitIdOrderByCompletedDateDesc(Long unitId);

    @Override
    @EntityGraph(attributePaths = {"unit"})
    List<UnitMaintenanceHistoryEntity> findAll();

    @EntityGraph(attributePaths = {"unit"})
    List<UnitMaintenanceHistoryEntity> findByUnitId(Long unitId);
}