package com.cornerstone.repository;

import com.cornerstone.dto.UnitMaintenanceHistoryDto;

import java.util.List;
import java.util.Optional;

public interface UnitMaintenanceHistoryRepository {

    List<UnitMaintenanceHistoryDto> getByUnitId(Long unitId);

    Optional<UnitMaintenanceHistoryDto> get(Long id);

    UnitMaintenanceHistoryDto save(UnitMaintenanceHistoryDto dto);

    void delete(Long id);
}
