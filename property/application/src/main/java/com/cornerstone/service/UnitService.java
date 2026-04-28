package com.cornerstone.service;

import com.cornerstone.dto.UnitDto;
import java.util.List;
import java.util.Optional;

public interface UnitService {
    List<UnitDto> getAll();
    Optional<UnitDto> get(Long id);
    UnitDto create(UnitDto unit);
    UnitDto update(Long id, UnitDto unit);
    void delete(Long id);
}