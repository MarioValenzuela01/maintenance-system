package com.cornerstone.repository;

import com.cornerstone.entity.UnitMaintenanceHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitMaintenanceHistoryJpaRepository extends JpaRepository<UnitMaintenanceHistoryEntity, Long> {

    List<UnitMaintenanceHistoryEntity> findByUnitIdOrderByCompletedDateDesc(Long unitId);
}