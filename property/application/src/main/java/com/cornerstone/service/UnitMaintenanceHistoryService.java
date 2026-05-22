package com.cornerstone.service;

import com.cornerstone.dto.UnitMaintenanceHistoryDto;

import java.util.List;
import java.util.Optional;

public interface UnitMaintenanceHistoryService {

    List<UnitMaintenanceHistoryDto> getAll();

    List<UnitMaintenanceHistoryDto> getByUnitId(Long unitId);

    Optional<UnitMaintenanceHistoryDto> get(Long id);

    UnitMaintenanceHistoryDto create(UnitMaintenanceHistoryDto dto);

    UnitMaintenanceHistoryDto update(Long id, UnitMaintenanceHistoryDto dto);

    void delete(Long id);
}