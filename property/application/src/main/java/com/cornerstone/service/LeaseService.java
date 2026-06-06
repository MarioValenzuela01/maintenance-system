package com.cornerstone.service;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.dto.TenantDto;
import com.cornerstone.dto.UnitDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LeaseService {

    List<LeaseDto> getAll();

    Optional<LeaseDto> get(Long id);

    LeaseDto create(LeaseDto lease);

    LeaseDto update(Long id, LeaseDto lease);

    LeaseDto endLease(Long leaseId, LocalDate endDate);

    List<TenantDto> getAvailableTenants();

    List<UnitDto> getAvailableUnits();

    Optional<LeaseDto> getActiveLeaseByUnitId(Long unitId);

    Optional<LeaseDto> getActiveLeaseByTenantId(Long tenantId);

    Optional<LeaseDto> getLeaseByUnitAndDate(Long unitId, LocalDate date);

    List<Long> getActiveUnitIds();
}