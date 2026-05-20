package com.cornerstone.service;

import com.cornerstone.dto.UnitMaintenanceHistoryDto;
import com.cornerstone.repository.UnitMaintenanceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitMaintenanceHistoryServiceImpl implements UnitMaintenanceHistoryService {

    private final UnitMaintenanceHistoryRepository repository;

    public UnitMaintenanceHistoryServiceImpl(UnitMaintenanceHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UnitMaintenanceHistoryDto> getByUnitId(Long unitId) {
        return repository.getByUnitId(unitId);
    }

    @Override
    public Optional<UnitMaintenanceHistoryDto> get(Long id) {
        return repository.get(id);
    }

    @Override
    public UnitMaintenanceHistoryDto create(UnitMaintenanceHistoryDto dto) {
        return repository.save(dto);
    }

    @Override
    public UnitMaintenanceHistoryDto update(Long id, UnitMaintenanceHistoryDto dto) {
        dto.setId(id);
        return repository.save(dto);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}