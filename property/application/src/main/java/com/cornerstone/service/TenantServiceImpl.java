package com.cornerstone.service;

import com.cornerstone.dto.TenantDto;
import com.cornerstone.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    // Inyección de dependencias a través del constructor
    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public List<TenantDto> getAll() {
        return tenantRepository.getAll();
    }

    @Override
    public Optional<TenantDto> get(Long id) {
        return tenantRepository.get(id);
    }

    @Override
    public TenantDto create(TenantDto tenant) {
        // Más adelante aquí inyectaremos un ValidationService para aplicar reglas de negocio
        return tenantRepository.save(tenant);
    }

    @Override
    public TenantDto update(Long id, TenantDto tenant) {
        tenant.setId(id);
        return tenantRepository.save(tenant);
    }

    @Override
    public void delete(Long id) {
        tenantRepository.delete(id);
    }
}