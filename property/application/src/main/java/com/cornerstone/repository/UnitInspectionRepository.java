package com.cornerstone.repository;

import com.cornerstone.dto.UnitInspectionDto;

import java.util.List;
import java.util.Optional;

public interface UnitInspectionRepository {

    List<UnitInspectionDto> getAll();

    List<UnitInspectionDto> search(Long unitId, String inspectionType);

    Optional<UnitInspectionDto> get(Long id);

    UnitInspectionDto save(UnitInspectionDto inspection);

    void delete(Long id);
}