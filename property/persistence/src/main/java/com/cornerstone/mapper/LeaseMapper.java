package com.cornerstone.mapper;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.entity.LeaseEntity;
import com.cornerstone.entity.TenantEntity;
import com.cornerstone.entity.UnitEntity;
import org.springframework.stereotype.Component;

@Component
public class LeaseMapper implements EntityMapper<LeaseDto, LeaseEntity> {

    @Override
    public LeaseDto toDTO(LeaseEntity entity) {
        if (entity == null) return null;

        Long tenantId = (entity.getTenant() != null) ? entity.getTenant().getId() : null;
        Long unitId = (entity.getUnit() != null) ? entity.getUnit().getId() : null;

        return new LeaseDto()
                .setId(entity.getId())
                .setTenantId(tenantId)
                .setUnitId(unitId)
                .setStartDate(entity.getStartDate())
                .setEndDate(entity.getEndDate());
    }

    @Override
    public LeaseEntity toEntity(LeaseDto dto) {
        if (dto == null) return null;

        LeaseEntity entity = new LeaseEntity()
                .setId(dto.getId())
                .setStartDate(dto.getStartDate())
                .setEndDate(dto.getEndDate());

        if (dto.getTenantId() != null) {
            entity.setTenant(new TenantEntity().setId(dto.getTenantId()));
        }
        if (dto.getUnitId() != null) {
            entity.setUnit(new UnitEntity().setId(dto.getUnitId()));
        }

        return entity;
    }
}