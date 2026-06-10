package com.cornerstone.service;

import com.cornerstone.dto.UnitInspectionDto;
import com.cornerstone.repository.UnitInspectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitInspectionServiceImpl implements UnitInspectionService {

    private final UnitInspectionRepository repository;

    public UnitInspectionServiceImpl(UnitInspectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UnitInspectionDto> getAll() {
        return repository.getAll();
    }

    @Override
    public List<UnitInspectionDto> search(Long unitId, String inspectionType) {
        return repository.search(unitId, inspectionType);
    }

    @Override
    public Optional<UnitInspectionDto> get(Long id) {
        return repository.get(id);
    }

    @Override
    public UnitInspectionDto create(UnitInspectionDto inspection) {
        inspection.setId(null);
        return repository.save(inspection);
    }

    @Override
    public UnitInspectionDto update(Long id, UnitInspectionDto inspection) {
        inspection.setId(id);
        return repository.save(inspection);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}