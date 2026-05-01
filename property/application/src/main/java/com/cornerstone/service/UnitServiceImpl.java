package com.cornerstone.service;

import com.cornerstone.dto.UnitDto;
import com.cornerstone.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public List<UnitDto> getAll() {
        return unitRepository.getAll();
    }

    @Override
    public Optional<UnitDto> get(Long id) {
        return unitRepository.get(id);
    }

    @Override
    public UnitDto create(UnitDto unit) {
        return unitRepository.save(unit);
    }

    @Override
    public UnitDto update(Long id, UnitDto unit) {
        unit.setId(id);
        return unitRepository.save(unit);
    }

    @Override
    public void delete(Long id) {
        unitRepository.delete(id);
    }

    @Override
    public Optional<UnitDto> getByUnitNumber(String unitNumber) {
        return unitRepository.getByUnitNumber(unitNumber);
    }
}