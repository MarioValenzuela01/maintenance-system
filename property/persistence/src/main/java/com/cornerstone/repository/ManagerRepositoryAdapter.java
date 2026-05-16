package com.cornerstone.repository;

import com.cornerstone.dto.ManagerDto;
import com.cornerstone.entity.ManagerEntity;
import com.cornerstone.entity.UnitEntity;
import com.cornerstone.mapper.ManagerMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public class ManagerRepositoryAdapter implements ManagerRepository {

    private final ManagerJpaRepository managerJpaRepository;
    private final UnitJpaRepository unitJpaRepository;

    public ManagerRepositoryAdapter(
            ManagerJpaRepository managerJpaRepository,
            UnitJpaRepository unitJpaRepository
    ) {
        this.managerJpaRepository = managerJpaRepository;
        this.unitJpaRepository = unitJpaRepository;
    }

    @Override
    public List<ManagerDto> getAll() {
        return managerJpaRepository.findAll()
                .stream()
                .map(ManagerMapper::toDto)
                .toList();
    }

    @Override
    public Optional<ManagerDto> get(Long id) {
        return managerJpaRepository.findById(id)
                .map(ManagerMapper::toDto);
    }

    @Override
    public ManagerDto save(ManagerDto managerDto) {
        ManagerEntity manager = new ManagerEntity();
        manager.setName(managerDto.getName());

        ManagerEntity savedManager = managerJpaRepository.save(manager);

        return ManagerMapper.toDto(savedManager);
    }

    @Override
    public void assignUnits(Long managerId, List<Long> unitIds) {
        ManagerEntity manager = managerJpaRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        List<UnitEntity> units = unitIds == null
                ? List.of()
                : unitJpaRepository.findAllById(unitIds);

        manager.setUnits(new HashSet<>(units));
        managerJpaRepository.save(manager);
    }
}