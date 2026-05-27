package com.cornerstone.service;

import com.cornerstone.dto.ManagerDto;
import com.cornerstone.repository.LeaseRepository;
import com.cornerstone.repository.ManagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final LeaseRepository leaseRepository;

    public ManagerServiceImpl(ManagerRepository managerRepository,
                              LeaseRepository leaseRepository) {
        this.managerRepository = managerRepository;
        this.leaseRepository = leaseRepository;
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
        List<Long> safeUnitIds = unitIds == null ? List.of() : unitIds;

        ManagerDto currentManager = managerRepository.get(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        for (Long unitId : safeUnitIds) {
            boolean alreadyAssignedToThisManager = currentManager.getUnitIds().contains(unitId);
            boolean hasActiveLease = leaseRepository.existsActiveLeaseByUnitId(unitId);

            if (hasActiveLease && !alreadyAssignedToThisManager) {
                throw new RuntimeException("Cannot assign occupied units to a manager.");
            }
        }

        managerRepository.assignUnits(managerId, safeUnitIds);
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