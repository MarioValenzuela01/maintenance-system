package com.cornerstone.mapper;

import com.cornerstone.dto.WorkOrderTimeLogDto;
import com.cornerstone.entity.AppUserEntity;
import com.cornerstone.entity.UnitEntity;
import com.cornerstone.entity.WorkOrderTimeLogEntity;

public class WorkOrderTimeLogMapper {

    public static WorkOrderTimeLogDto toDto(WorkOrderTimeLogEntity entity) {
        if (entity == null) {
            return null;
        }

        WorkOrderTimeLogDto dto = new WorkOrderTimeLogDto()
                .setId(entity.getId())
                .setWorkDate(entity.getWorkDate())
                .setStartTime(entity.getStartTime())
                .setEndTime(entity.getEndTime())
                .setMinutesWorked(entity.getMinutesWorked())
                .setNotes(entity.getNotes())
                .setCreatedDate(entity.getCreatedDate());

        if (entity.getWorkOrder() != null) {
            dto.setWorkOrderId(entity.getWorkOrder().getId());
        }

        UnitEntity unit = entity.getUnit();

        if (unit != null) {
            dto.setUnitId(unit.getId());
            dto.setUnitNumber(unit.getUnitNumber());
            dto.setUnitDisplayLabel(buildUnitLabel(unit));
        }

        AppUserEntity user = entity.getUser();

        if (user != null) {
            dto.setUserId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setUserFullName(user.getFullName());
        }

        return dto;
    }

    private static String buildUnitLabel(UnitEntity unit) {
        if (unit.getDisplayName() != null && !unit.getDisplayName().isBlank()) {
            return unit.getDisplayName() + " - Unit " + unit.getUnitNumber();
        }

        return "Unit " + unit.getUnitNumber();
    }
}