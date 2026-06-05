package com.cornerstone.service;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.dto.TenantDto;
import com.cornerstone.dto.UnitDto;
import com.cornerstone.repository.LeaseRepository;
import com.cornerstone.repository.TenantRepository;
import com.cornerstone.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LeaseServiceImpl implements LeaseService {

    private final LeaseRepository leaseRepository;
    private final TenantRepository tenantRepository;
    private final UnitRepository unitRepository;
    private final ManagerService managerService;

    public LeaseServiceImpl(LeaseRepository leaseRepository,
                            TenantRepository tenantRepository,
                            UnitRepository unitRepository,
                            ManagerService managerService) {
        this.leaseRepository = leaseRepository;
        this.tenantRepository = tenantRepository;
        this.unitRepository = unitRepository;
        this.managerService = managerService;
    }

    @Override
    public List<LeaseDto> getAll() {
        return leaseRepository.getAll();
    }

    @Override
    public Optional<LeaseDto> get(Long id) {
        return leaseRepository.get(id);
    }

    @Override
    public LeaseDto create(LeaseDto lease) {

        if (lease.getStartDate() == null) {
            lease.setStartDate(LocalDate.now());
        }

        lease.setEndDate(null);



        return leaseRepository.save(lease);
    }

    @Override
    public LeaseDto endLease(Long leaseId, LocalDate endDate) {
        LeaseDto lease = leaseRepository.get(leaseId)
                .orElseThrow(() -> new RuntimeException("Lease not found"));

        lease.setEndDate(endDate != null ? endDate : LocalDate.now());

        return leaseRepository.save(lease);
    }

    @Override
    public LeaseDto update(Long id, LeaseDto lease) {
        lease.setId(id);



        return leaseRepository.save(lease);
    }

    // 🔥 NUEVO: tenants disponibles
    @Override
    public List<TenantDto> getAvailableTenants() {
        Set<Long> activeTenantIds = new HashSet<>(leaseRepository.getActiveTenantIds());

        return tenantRepository.getAll()
                .stream()
                .filter(tenant -> Boolean.TRUE.equals(tenant.getActive()))
                .filter(tenant -> tenant.getId() != null)
                .filter(tenant -> !activeTenantIds.contains(tenant.getId()))
                .toList();
    }


    // 🔥 NUEVO: unidades disponibles
    @Override
    public List<UnitDto> getAvailableUnits() {
        Set<Long> activeUnitIds = new HashSet<>(leaseRepository.getActiveUnitIds());
        Set<Long> managedUnitIds = new HashSet<>(managerService.getManagedUnitIds());

        return unitRepository.getAll()
                .stream()
                .filter(unit -> unit.getId() != null)
                .filter(unit -> !activeUnitIds.contains(unit.getId()))
                .filter(unit -> !managedUnitIds.contains(unit.getId()))
                .toList();
    }

    @Override
    public Optional<LeaseDto> getActiveLeaseByUnitId(Long unitId) {
        return leaseRepository.getActiveLeaseByUnitId(unitId);
    }

    @Override
    public Optional<LeaseDto> getActiveLeaseByTenantId(Long tenantId) {
        return leaseRepository.getActiveLeaseByTenantId(tenantId);
    }

    @Override
    public Optional<LeaseDto> getLeaseByUnitAndDate(Long unitId, LocalDate date) {
        return leaseRepository.getLeaseByUnitAndDate(unitId, date);
    }


}