package com.cornerstone.service;

import com.cornerstone.dto.TenantDto;
import com.cornerstone.repository.LeaseRepository;
import com.cornerstone.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final LeaseRepository leaseRepository;

    // Inyección de dependencias
    public TenantServiceImpl(TenantRepository tenantRepository,
                             LeaseRepository leaseRepository) {
        this.tenantRepository = tenantRepository;
        this.leaseRepository = leaseRepository;
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

        tenant.setUpdatedAt(LocalDateTime.now());
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
        tenant.setUpdatedAt(LocalDateTime.now());
        return tenantRepository.save(tenant);
    }

    @Override
    public void delete(Long id) {

        boolean hasActiveLease = leaseRepository.existsActiveLeaseByTenantId(id);

        if (hasActiveLease) {
            throw new RuntimeException("Cannot deactivate tenant with an active lease.");
        }

        TenantDto tenant = tenantRepository.get(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        tenant.setActive(false);
        tenant.setUpdatedAt(LocalDateTime.now());

        tenantRepository.save(tenant);
    }
}