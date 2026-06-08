package com.cornerstone.mapper;

import com.cornerstone.dto.WorkOrderDto;
import com.cornerstone.entity.AppUserEntity;
import com.cornerstone.entity.UnitEntity;
import com.cornerstone.entity.WorkOrderEntity;

public class WorkOrderMapper {

    public static WorkOrderDto toDto(WorkOrderEntity entity) {
        if (entity == null) {
            return null;
        }

        WorkOrderDto dto = new WorkOrderDto()
                .setId(entity.getId())
                .setWorkType(entity.getWorkType())
                .setPriority(entity.getPriority())
                .setStatus(entity.getStatus())
                .setDescription(entity.getDescription())
                .setNotes(entity.getNotes())
                .setCreatedDate(entity.getCreatedDate())
                .setDueDate(entity.getDueDate())
                .setCompletedDate(entity.getCompletedDate());

        UnitEntity unit = entity.getUnit();

        if (unit != null) {
            dto.setUnitId(unit.getId());
            dto.setUnitNumber(unit.getUnitNumber());
            dto.setUnitDisplayName(unit.getDisplayName());
            dto.setUnitDisplayLabel(buildUnitLabel(unit));
        }

        AppUserEntity assignedTo = entity.getAssignedTo();

        if (assignedTo != null) {
            dto.setAssignedToUserId(assignedTo.getId());
            dto.setAssignedToUsername(assignedTo.getUsername());
            dto.setAssignedToFullName(assignedTo.getFullName());
        }

        AppUserEntity createdBy = entity.getCreatedBy();

        if (createdBy != null) {
            dto.setCreatedByUserId(createdBy.getId());
            dto.setCreatedByUsername(createdBy.getUsername());
            dto.setCreatedByFullName(createdBy.getFullName());
        }

        return dto;
    }

    private static String buildUnitLabel(UnitEntity unit) {
        String unitNumber = unit.getUnitNumber();
        String displayName = unit.getDisplayName();

        if (displayName != null && !displayName.isBlank()) {
            return displayName + " - Unit " + unitNumber;
        }

        return "Unit " + unitNumber;
    }
}