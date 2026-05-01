package com.cornerstone.repository;

import com.cornerstone.entity.LeaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaseJpaRepository extends JpaRepository<LeaseEntity, Long> {

    boolean existsByTenantIdAndEndDateIsNull(Long tenantId);

    boolean existsByUnitIdAndEndDateIsNull(Long unitId);
}