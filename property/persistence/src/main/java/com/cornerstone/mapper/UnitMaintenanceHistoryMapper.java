package com.cornerstone.mapper;

import com.cornerstone.dto.UnitMaintenanceHistoryDto;
import com.cornerstone.entity.UnitMaintenanceHistoryEntity;

public class UnitMaintenanceHistoryMapper {

    public static UnitMaintenanceHistoryDto toDto(UnitMaintenanceHistoryEntity entity) {
        if (entity == null) return null;

        return new UnitMaintenanceHistoryDto()
                .setId(entity.getId())
                .setUnitId(entity.getUnitId())
                .setCategory(entity.getCategory())
                .setItemName(entity.getItemName())
                .setCompletedDate(entity.getCompletedDate())
                .setNotes(entity.getNotes());
    }

    public static UnitMaintenanceHistoryEntity toEntity(UnitMaintenanceHistoryDto dto) {
        if (dto == null) return null;

        return new UnitMaintenanceHistoryEntity()
                .setId(dto.getId())
                .setUnitId(dto.getUnitId())
                .setCategory(dto.getCategory())
                .setItemName(dto.getItemName())
                .setCompletedDate(dto.getCompletedDate())
                .setNotes(dto.getNotes());
    }
}