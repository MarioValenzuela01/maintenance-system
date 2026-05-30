package com.cornerstone.repository;

import com.cornerstone.dto.UnitMaintenanceHistoryDto;
import com.cornerstone.mapper.UnitMaintenanceHistoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UnitMaintenanceHistoryRepositoryAdapter implements UnitMaintenanceHistoryRepository {



    private final UnitMaintenanceHistoryJpaRepository jpaRepository;

    public UnitMaintenanceHistoryRepositoryAdapter(UnitMaintenanceHistoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<UnitMaintenanceHistoryDto> getAll() {

        return jpaRepository.findAll()
                .stream()
                .map(UnitMaintenanceHistoryMapper::toDto)
                .toList();
    }

    @Override
    public List<UnitMaintenanceHistoryDto> getByUnitId(Long unitId) {
        return jpaRepository.findByUnitIdOrderByCompletedDateDesc(unitId)
                .stream()
                .map(UnitMaintenanceHistoryMapper::toDto)
                .toList();
    }

    @Override
    public Optional<UnitMaintenanceHistoryDto> get(Long id) {
        return jpaRepository.findById(id)
                .map(UnitMaintenanceHistoryMapper::toDto);
    }

    @Override
    public UnitMaintenanceHistoryDto save(UnitMaintenanceHistoryDto dto) {
        var entity = UnitMaintenanceHistoryMapper.toEntity(dto);
        var saved = jpaRepository.save(entity);
        return UnitMaintenanceHistoryMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }
}