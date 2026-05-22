package com.cornerstone.service;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.dto.TenantDto;
import com.cornerstone.dto.UnitDto;
import com.cornerstone.repository.LeaseRepository;
import com.cornerstone.repository.TenantRepository;
import com.cornerstone.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeaseServiceImpl implements LeaseService {

    private final LeaseRepository leaseRepository;
    private final TenantRepository tenantRepository;
    private final UnitRepository unitRepository;

    public LeaseServiceImpl(LeaseRepository leaseRepository,
                            TenantRepository tenantRepository,
                            UnitRepository unitRepository) {
        this.leaseRepository = leaseRepository;
        this.tenantRepository = tenantRepository;
        this.unitRepository = unitRepository;
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

        if (lease.getAdultsCount() == null) lease.setAdultsCount(0);
        if (lease.getChildrenCount() == null) lease.setChildrenCount(0);
        if (lease.getSeniorsCount() == null) lease.setSeniorsCount(0);
        if (lease.getPetsCount() == null) lease.setPetsCount(0);
        if (lease.getCarsCount() == null) lease.setCarsCount(0);
        if (lease.getSmokers() == null) lease.setSmokers(false);

        return leaseRepository.save(lease);
    }

    @Override
    public LeaseDto endLease(Long leaseId) {
        Optional<LeaseDto> existingLease = leaseRepository.get(leaseId);

        if (existingLease.isPresent()) {
            LeaseDto lease = existingLease.get();
            lease.setEndDate(LocalDate.now());
            return leaseRepository.save(lease);
        }

        throw new IllegalArgumentException("Contrato no encontrado");
    }

    @Override
    public LeaseDto update(Long id, LeaseDto lease) {
        lease.setId(id);

        if (lease.getAdultsCount() == null) lease.setAdultsCount(0);
        if (lease.getChildrenCount() == null) lease.setChildrenCount(0);
        if (lease.getSeniorsCount() == null) lease.setSeniorsCount(0);
        if (lease.getPetsCount() == null) lease.setPetsCount(0);
        if (lease.getCarsCount() == null) lease.setCarsCount(0);
        if (lease.getSmokers() == null) lease.setSmokers(false);

        return leaseRepository.save(lease);
    }

    // 🔥 NUEVO: tenants disponibles
    @Override
    public List<TenantDto> getAvailableTenants() {
        return tenantRepository.getAll()
                .stream()
                .filter(t -> !leaseRepository.existsActiveLeaseByTenantId(t.getId()))
                .toList();
    }

    // 🔥 NUEVO: unidades disponibles
    @Override
    public List<UnitDto> getAvailableUnits() {
        return unitRepository.getAll()
                .stream()
                .filter(u -> !leaseRepository.existsActiveLeaseByUnitId(u.getId()))
                .toList();
    }

    @Override
    public Optional<LeaseDto> getActiveLeaseByUnitId(Long unitId) {
        return leaseRepository.getAll()
                .stream()
                .filter(l -> l.getUnitId() != null)
                .filter(l -> l.getUnitId().equals(unitId))
                .filter(l -> l.getEndDate() == null)
                .findFirst();
    }

    @Override
    public Optional<LeaseDto> getActiveLeaseByTenantId(Long tenantId) {
        return leaseRepository.getAll()
                .stream()
                .filter(l -> l.getTenantId() != null)
                .filter(l -> l.getTenantId().equals(tenantId))
                .filter(l -> l.getEndDate() == null)
                .findFirst();
    }

    @Override
    public Optional<LeaseDto> getLeaseByUnitAndDate(Long unitId, LocalDate date) {
        return leaseRepository.getLeaseByUnitAndDate(unitId, date);
    }


}