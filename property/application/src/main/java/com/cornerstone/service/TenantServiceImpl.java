package com.cornerstone.service;

import com.cornerstone.dto.TenantDto;
import com.cornerstone.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    // Inyección de dependencias
    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public List<TenantDto> getAll() {
        return tenantRepository.getAll()
                .stream()
                .filter(t -> Boolean.TRUE.equals(t.getActive()))
                .toList();
    }

    @Override
    public Optional<TenantDto> get(Long id) {
        return tenantRepository.get(id);
    }

    @Override
    public TenantDto create(TenantDto tenant) {
        if (tenant.getEmail() != null && !tenant.getEmail().isBlank()) {

            boolean exists = tenantRepository
                    .existsByEmailAndActiveTrue(tenant.getEmail());

            if (exists) {
                throw new RuntimeException("Email already used by an active tenant");
            }
        }

        return tenantRepository.save(tenant);
    }

    @Override
    public TenantDto update(Long id, TenantDto tenant) {
        if (tenant.getEmail() != null && !tenant.getEmail().isBlank()) {

            boolean exists = tenantRepository
                    .existsByEmailAndActiveTrueAndIdNot(tenant.getEmail(), id);

            if (exists) {
                throw new RuntimeException("Email already used by another active tenant");
            }
        }


        tenant.setId(id);
        return tenantRepository.save(tenant);
    }

    @Override
    public void delete(Long id) {
        Optional<TenantDto> tenantOpt = tenantRepository.get(id);

        if (tenantOpt.isEmpty()) {
            return;
        }

        TenantDto tenant = tenantOpt.get();
        tenant.setActive(false); // 👈 soft delete

        tenantRepository.save(tenant);
    }
}