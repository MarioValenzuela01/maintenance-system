package com.cornerstone.repository;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.entity.LeaseEntity;
import com.cornerstone.entity.TenantEntity;  // AGREGADO
import com.cornerstone.entity.UnitEntity;    // AGREGADO
import com.cornerstone.mapper.LeaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LeaseRepositoryAdapter implements LeaseRepository {

    private final LeaseJpaRepository jpaRepository;
    private final LeaseMapper mapper;

    // AGREGADO: necesitamos estos dos repositorios para buscar las entidades
    private final TenantJpaRepository tenantJpaRepository;
    private final UnitJpaRepository unitJpaRepository;

    // AGREGADO: inyectamos los dos nuevos repositorios en el constructor
    public LeaseRepositoryAdapter(LeaseJpaRepository jpaRepository,
                                  LeaseMapper mapper,
                                  TenantJpaRepository tenantJpaRepository,
                                  UnitJpaRepository unitJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.tenantJpaRepository = tenantJpaRepository; // AGREGADO
        this.unitJpaRepository = unitJpaRepository;     // AGREGADO
    }

    @Override
    public List<LeaseDto> getAll() {
        return mapper.toDTO(jpaRepository.findAll());
    }

    @Override
    public Optional<LeaseDto> get(Long id) {
        return mapper.toDTO(jpaRepository.findById(id));
    }

    @Override
    public LeaseDto save(LeaseDto lease) {
        LeaseEntity entity = mapper.toEntity(lease);

        // AGREGADO: asignamos el TenantEntity real usando el tenantId del DTO
        TenantEntity tenant = tenantJpaRepository.findById(lease.getTenantId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tenant no encontrado con ID: " + lease.getTenantId()));
        entity.setTenant(tenant); // AGREGADO

        // AGREGADO: asignamos el UnitEntity real usando el unitId del DTO
        UnitEntity unit = unitJpaRepository.findById(lease.getUnitId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unit no encontrado con ID: " + lease.getUnitId()));
        entity.setUnit(unit); // AGREGADO

        LeaseEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsActiveLeaseByTenantId(Long tenantId) {
        return jpaRepository.existsByTenantIdAndEndDateIsNull(tenantId);
    }

    @Override
    public boolean existsActiveLeaseByUnitId(Long unitId) {
        return jpaRepository.existsByUnitIdAndEndDateIsNull(unitId);
    }
}