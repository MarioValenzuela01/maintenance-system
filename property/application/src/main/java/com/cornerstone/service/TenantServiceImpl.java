package com.cornerstone.service;

import com.cornerstone.dto.TenantDto;
import com.cornerstone.repository.LeaseRepository;
import com.cornerstone.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final LeaseRepository leaseRepository;

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
        if (tenant.getActive() == null) {
            tenant.setActive(true);
        }

        tenant.setUpdatedAt(LocalDateTime.now());
        return tenantRepository.save(tenant);
    }

    @Override
    public TenantDto update(Long id, TenantDto tenant) {
        tenant.setId(id);

        if (tenant.getActive() == null) {
            tenant.setActive(true);
        }

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

    @Override
    public Optional<TenantDto> findPossibleDuplicate(TenantDto tenant) {
        if (tenant == null) {
            return Optional.empty();
        }

        String firstName = normalize(tenant.getFirstName());
        String lastName = normalize(tenant.getLastName());
        String email = normalize(tenant.getEmail());
        String phone = normalize(tenant.getPhone());

        if (firstName.isBlank() || lastName.isBlank()) {
            return Optional.empty();
        }

        return getAll()
                .stream()
                .filter(existing -> tenant.getId() == null || !tenant.getId().equals(existing.getId()))
                .filter(existing ->
                        normalize(existing.getFirstName()).equals(firstName)
                                && normalize(existing.getLastName()).equals(lastName)
                )
                .filter(existing ->
                        (!email.isBlank() && normalize(existing.getEmail()).equals(email))
                                || (!phone.isBlank() && normalize(existing.getPhone()).equals(phone))
                )
                .findFirst();
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}