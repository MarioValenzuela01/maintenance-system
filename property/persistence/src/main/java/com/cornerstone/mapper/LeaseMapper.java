package com.cornerstone.mapper;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.entity.LeaseEntity;
import com.cornerstone.entity.TenantEntity;
import com.cornerstone.entity.UnitEntity;
import com.cornerstone.repository.TenantJpaRepository;
import com.cornerstone.repository.UnitJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class LeaseMapper implements EntityMapper<LeaseDto, LeaseEntity> {

    private final TenantJpaRepository tenantRepository;
    private final UnitJpaRepository unitRepository;

    public LeaseMapper(TenantJpaRepository tenantRepository,
                       UnitJpaRepository unitRepository) {
        this.tenantRepository = tenantRepository;
        this.unitRepository = unitRepository;
    }

    @Override
    public LeaseDto toDTO(LeaseEntity entity) {
        if (entity == null) return null;

        return new LeaseDto()
                .setId(entity.getId())
                .setTenantId(entity.getTenant() != null ? entity.getTenant().getId() : null)
                .setUnitId(entity.getUnit() != null ? entity.getUnit().getId() : null)
                .setStartDate(entity.getStartDate())
                .setEndDate(entity.getEndDate())
                .setRentAmount(entity.getRentAmount())
                .setSubsidyAmount(entity.getSubsidyAmount())
                .setTenantContribution(entity.getTenantContribution())
                .setAdultsCount(entity.getAdultsCount())
                .setChildrenCount(entity.getChildrenCount())
                .setSeniorsCount(entity.getSeniorsCount())
                .setSmokers(entity.getSmokers())
                .setPetsCount(entity.getPetsCount())
                .setCarsCount(entity.getCarsCount())
                .setProgramNotes(entity.getProgramNotes());
    }

    @Override
    public LeaseEntity toEntity(LeaseDto dto) {
        if (dto == null) return null;

        TenantEntity tenant = null;
        UnitEntity unit = null;

        if (dto.getTenantId() != null) {
            tenant = tenantRepository.findById(dto.getTenantId()).orElse(null);
        }

        if (dto.getUnitId() != null) {
            unit = unitRepository.findById(dto.getUnitId()).orElse(null);
        }

        return new LeaseEntity()
                .setId(dto.getId())
                .setTenant(tenant)
                .setUnit(unit)
                .setStartDate(dto.getStartDate())
                .setEndDate(dto.getEndDate())
                .setRentAmount(dto.getRentAmount())
                .setSubsidyAmount(dto.getSubsidyAmount())
                .setTenantContribution(dto.getTenantContribution())
                .setAdultsCount(dto.getAdultsCount())
                .setChildrenCount(dto.getChildrenCount())
                .setSeniorsCount(dto.getSeniorsCount())
                .setSmokers(dto.getSmokers())
                .setPetsCount(dto.getPetsCount())
                .setCarsCount(dto.getCarsCount())
                .setProgramNotes(dto.getProgramNotes());
    }
}