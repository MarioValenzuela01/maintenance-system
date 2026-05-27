package com.cornerstone.service;

import com.cornerstone.dto.TenantDto;
import java.util.List;
import java.util.Optional;

public interface TenantService {
    List<TenantDto> getAll();
    Optional<TenantDto> get(Long id);
    TenantDto create(TenantDto tenant);
    TenantDto update(Long id, TenantDto tenant);
    void delete(Long id);
    Optional<TenantDto> findPossibleDuplicate(TenantDto tenant);
}
