package com.cornerstone.repository;

import com.cornerstone.entity.UnitInspectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitInspectionJpaRepository extends JpaRepository<UnitInspectionEntity, Long> {

    List<UnitInspectionEntity> findByUnitIdOrderByInspectionDateDesc(Long unitId);

    List<UnitInspectionEntity> findByInspectionTypeContainingIgnoreCaseOrderByInspectionDateDesc(String inspectionType);

    List<UnitInspectionEntity> findByUnitIdAndInspectionTypeContainingIgnoreCaseOrderByInspectionDateDesc(
            Long unitId,
            String inspectionType
    );

    List<UnitInspectionEntity> findAllByOrderByInspectionDateDesc();
}