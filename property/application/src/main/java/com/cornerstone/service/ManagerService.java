package com.cornerstone.service;

import com.cornerstone.dto.ManagerDto;

import java.util.List;
import java.util.Optional;

public interface ManagerService {

    List<ManagerDto> getAll();

    Optional<ManagerDto> get(Long id);

    ManagerDto create(ManagerDto managerDto);

    void assignUnits(Long managerId, List<Long> unitIds);

    List<Long> getManagedUnitIds();
}