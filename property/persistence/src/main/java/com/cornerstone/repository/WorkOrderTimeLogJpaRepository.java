package com.cornerstone.repository;

import com.cornerstone.entity.WorkOrderTimeLogEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cornerstone.entity.UnitEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WorkOrderTimeLogJpaRepository extends JpaRepository<WorkOrderTimeLogEntity, Long> {

    @EntityGraph(attributePaths = {"workOrder", "unit", "user"})
    List<WorkOrderTimeLogEntity> findByWorkOrder_IdOrderByWorkDateDescStartTimeDesc(Long workOrderId);

    @EntityGraph(attributePaths = {"workOrder", "unit", "user"})
    List<WorkOrderTimeLogEntity> findByUnit_UnitNumberInAndWorkDateBetweenOrderByWorkDateAscStartTimeAsc(
            List<String> unitNumbers,
            LocalDate fromDate,
            LocalDate toDate
    );

    @Modifying
    @Query("""
        UPDATE WorkOrderTimeLogEntity log
        SET log.unit = :unit
        WHERE log.workOrder.id = :workOrderId
        """)
    void updateUnitByWorkOrderId(@Param("workOrderId") Long workOrderId,
                                 @Param("unit") UnitEntity unit);
}