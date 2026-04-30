package com.cornerstone.service;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.repository.LeaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeaseServiceImpl implements LeaseService {

    private final LeaseRepository leaseRepository;


    public LeaseServiceImpl(LeaseRepository leaseRepository) {
        this.leaseRepository = leaseRepository;
    }

    @Override public List<LeaseDto> getAll() { return leaseRepository.getAll(); }
    @Override public Optional<LeaseDto> get(Long id) { return leaseRepository.get(id); }

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
        // Regla de negocio: En lugar de borrar de la base de datos (y perder historial),
        // simplemente marcamos la fecha de fin (endDate) del contrato con el día de hoy.
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
}
