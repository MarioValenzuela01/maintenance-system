package com.cornerstone.mapper;

import com.cornerstone.dto.ManagerDto;
import com.cornerstone.entity.ManagerEntity;
import com.cornerstone.entity.UnitEntity;

import java.util.List;

public class ManagerMapper {

    public static ManagerDto toDto(ManagerEntity entity) {
        ManagerDto dto = new ManagerDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());

        dto.setUnitIds(
                entity.getUnits()
                        .stream()
                        .map(UnitEntity::getId)
                        .toList()
        );

        dto.setUnitNumbers(
                entity.getUnits()
                        .stream()
                        .map(unit -> {
                            if (unit.getDisplayName() != null && !unit.getDisplayName().isBlank()) {
                                return unit.getDisplayName();
                            }
                            return unit.getUnitNumber();
                        })
                        .toList()
        );

        return dto;
    }

    public static List<ManagerDto> toDtoList(List<ManagerEntity> entities) {
        return entities.stream()
                .map(ManagerMapper::toDto)
                .toList();
    }
}