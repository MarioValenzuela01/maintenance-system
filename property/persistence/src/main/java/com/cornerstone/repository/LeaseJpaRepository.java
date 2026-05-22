package com.cornerstone.repository;

import com.cornerstone.entity.LeaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaseJpaRepository extends JpaRepository<LeaseEntity, Long> {

    boolean existsByTenantIdAndEndDateIsNull(Long tenantId);

    boolean existsByUnitIdAndEndDateIsNull(Long unitId);

    Optional<LeaseEntity> findFirstByTenantIdAndEndDateIsNull(Long tenantId);

    Optional<LeaseEntity> findFirstByUnitIdAndEndDateIsNull(Long unitId);

    List<LeaseEntity> findByUnitId(Long unitId);
}