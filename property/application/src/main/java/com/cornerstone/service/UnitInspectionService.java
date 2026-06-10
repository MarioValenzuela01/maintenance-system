package com.cornerstone.service;

import com.cornerstone.dto.UnitInspectionDto;

import java.util.List;
import java.util.Optional;

public interface UnitInspectionService {

    List<UnitInspectionDto> getAll();

    List<UnitInspectionDto> search(Long unitId, String inspectionType);

    Optional<UnitInspectionDto> get(Long id);

    UnitInspectionDto create(UnitInspectionDto inspection);

    UnitInspectionDto update(Long id, UnitInspectionDto inspection);

    void delete(Long id);
}