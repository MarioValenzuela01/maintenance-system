package com.cornerstone.repository;

import com.cornerstone.dto.TenantDto;
import com.cornerstone.entity.TenantEntity;
import com.cornerstone.mapper.TenantMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TenantRepositoryAdapter implements TenantRepository {

    private final TenantJpaRepository jpaRepository;
    private final TenantMapper mapper;

    // Inyección de dependencias
    public TenantRepositoryAdapter(TenantJpaRepository jpaRepository, TenantMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TenantDto> getAll() {
        List<TenantEntity> entities = jpaRepository.findAll();
        // Gracias a los default methods que pusimos en EntityMapper, esto funciona automático:
        return mapper.toDTO(entities);
    }

    @Override
    public Optional<TenantDto> get(Long id) {
        Optional<TenantEntity> entity = jpaRepository.findById(id);
        return mapper.toDTO(entity);
    }

    @Override
    public TenantDto save(TenantDto tenant) {
        TenantEntity entity = mapper.toEntity(tenant);
        TenantEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }
}