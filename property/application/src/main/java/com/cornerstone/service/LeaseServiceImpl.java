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
        // Regla de negocio: Si no se manda fecha de inicio, empieza hoy.
        // La fecha de fin siempre es nula al crear un contrato nuevo.
        if (lease.getStartDate() == null) {
            lease.setStartDate(LocalDate.now());
        }
        lease.setEndDate(null);
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
}
