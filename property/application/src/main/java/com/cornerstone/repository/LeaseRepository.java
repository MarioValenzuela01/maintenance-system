package com.cornerstone.repository;

import com.cornerstone.dto.LeaseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LeaseRepository {

    List<LeaseDto> getAll();

    Optional<LeaseDto> get(Long id);

    LeaseDto save(LeaseDto lease);

    void delete(Long id);

    // 🔥 NUEVO
    boolean existsActiveLeaseByTenantId(Long tenantId);

    boolean existsActiveLeaseByUnitId(Long unitId);

    Optional<LeaseDto> getActiveLeaseByTenantId(Long tenantId);

    Optional<LeaseDto> getActiveLeaseByUnitId(Long unitId);

    Optional<LeaseDto> getLeaseByUnitAndDate(Long unitId, LocalDate date);

    List<Long> getActiveTenantIds();

    List<Long> getActiveUnitIds();


}