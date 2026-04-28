package com.cornerstone.service;

import com.cornerstone.dto.LeaseDto;
import java.util.List;
import java.util.Optional;

public interface LeaseService {
    List<LeaseDto> getAll();
    Optional<LeaseDto> get(Long id);
    LeaseDto create(LeaseDto lease);

    // Regla de negocio: Registrar que un arrendatario dejó la unidad
    LeaseDto endLease(Long leaseId);
}