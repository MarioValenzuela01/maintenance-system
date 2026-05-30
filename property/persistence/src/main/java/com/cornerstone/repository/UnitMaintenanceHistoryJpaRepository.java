package com.cornerstone.repository;

import com.cornerstone.entity.UnitMaintenanceHistoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitMaintenanceHistoryJpaRepository extends JpaRepository<UnitMaintenanceHistoryEntity, Long> {

    @Query(value = """
            SELECT
                h.id AS id,
                h.unit_id AS unitId,
                h.category AS category,
                h.item_name AS itemName,
                h.completed_date AS completedDate,
                h.notes AS notes,
                h.tenant_id_at_time AS tenantIdAtTime,
                h.tenant_name_at_time AS tenantNameAtTime,
                u.unit_number AS unitNumber,
                COALESCE(NULLIF(u.display_name, ''), u.unit_number) AS unitDisplayName
            FROM unit_maintenance_history h
            LEFT JOIN units u ON u.id = h.unit_id
            ORDER BY h.completed_date DESC
            """, nativeQuery = true)
    List<UnitMaintenanceHistoryListProjection> findAllForList();

    @EntityGraph(attributePaths = {"unit"})
    List<UnitMaintenanceHistoryEntity> findByUnitIdOrderByCompletedDateDesc(Long unitId);
}