package com.cornerstone.repository;

import com.cornerstone.dto.TenantDto;
import java.util.List;
import java.util.Optional;

public interface TenantRepository {
    List<TenantDto> getAll();
    Optional<TenantDto> get(Long id);
    TenantDto save(TenantDto tenant);
    void delete(Long id);
}