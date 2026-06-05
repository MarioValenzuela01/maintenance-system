package com.cornerstone.repository;

import com.cornerstone.entity.LeaseEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaseJpaRepository extends JpaRepository<LeaseEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"tenant", "unit"})
    List<LeaseEntity> findAll();

    @EntityGraph(attributePaths = {"tenant", "unit"})
    Optional<LeaseEntity> findFirstByTenantIdAndEndDateIsNull(Long tenantId);

    @EntityGraph(attributePaths = {"tenant", "unit"})
    Optional<LeaseEntity> findFirstByUnitIdAndEndDateIsNull(Long unitId);

    @EntityGraph(attributePaths = {"tenant", "unit"})
    List<LeaseEntity> findByUnitId(Long unitId);

    boolean existsByTenantIdAndEndDateIsNull(Long tenantId);

    boolean existsByUnitIdAndEndDateIsNull(Long unitId);

    @Query("select l.tenant.id from LeaseEntity l where l.endDate is null")
    List<Long> findActiveTenantIds();

    @Query("select l.unit.id from LeaseEntity l where l.endDate is null")
    List<Long> findActiveUnitIds();
}