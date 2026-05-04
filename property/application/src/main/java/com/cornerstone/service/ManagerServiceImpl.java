package com.cornerstone.service;

import com.cornerstone.dto.ManagerDto;
import com.cornerstone.repository.ManagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public List<ManagerDto> getAll() {
        return managerRepository.getAll();
    }

    @Override
    public Optional<ManagerDto> get(Long id) {
        return managerRepository.get(id);
    }

    @Override
    public ManagerDto create(ManagerDto managerDto) {
        return managerRepository.save(managerDto);
    }

    @Override
    public void assignUnits(Long managerId, List<Long> unitIds) {
        managerRepository.assignUnits(managerId, unitIds);
    }

    @Override
    public List<Long> getManagedUnitIds() {
        return managerRepository.getAll()
                .stream()
                .flatMap(manager -> manager.getUnitIds().stream())
                .distinct()
                .toList();
    }
}