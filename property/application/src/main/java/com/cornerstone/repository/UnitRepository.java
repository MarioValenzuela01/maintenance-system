package com.cornerstone.repository;

import com.cornerstone.dto.UnitDto;
import java.util.List;
import java.util.Optional;

public interface UnitRepository {
    List<UnitDto> getAll();
    Optional<UnitDto> get(Long id);
    Optional<UnitDto> getByUnitNumber(String unitNumber);
    UnitDto save(UnitDto unit);
    void delete(Long id);
}