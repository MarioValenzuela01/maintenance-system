package com.cornerstone.mapper;

import com.cornerstone.dto.UnitDto;
import com.cornerstone.entity.UnitEntity;
import org.springframework.stereotype.Component;

@Component
public class UnitMapper implements EntityMapper<UnitDto, UnitEntity> {

    @Override
    public UnitDto toDTO(UnitEntity entity) {
        if (entity == null) return null;
        return new UnitDto()
                .setId(entity.getId())
                .setUnitNumber(entity.getUnitNumber())
                .setAddress(entity.getAddress())
                .setStatus(entity.getStatus())
                .setOwnershipType(entity.getOwnershipType())
                .setProgramType(entity.getProgramType())
                .setManagedByCornerstone(entity.getManagedByCornerstone())
                .setNotes(entity.getNotes());
    }

    @Override
    public UnitEntity toEntity(UnitDto dto) {
        if (dto == null) return null;
        return new UnitEntity()
                .setId(dto.getId())
                .setUnitNumber(dto.getUnitNumber())
                .setAddress(dto.getAddress())
                .setStatus(dto.getStatus())
                .setOwnershipType(dto.getOwnershipType())
                .setProgramType(dto.getProgramType())
                .setManagedByCornerstone(dto.getManagedByCornerstone())
                .setNotes(dto.getNotes());
    }
}