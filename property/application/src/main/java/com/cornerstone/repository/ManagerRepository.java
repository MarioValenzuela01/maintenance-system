package com.cornerstone.repository;

import com.cornerstone.dto.ManagerDto;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository {

    List<ManagerDto> getAll();

    Optional<ManagerDto> get(Long id);

    ManagerDto save(ManagerDto managerDto);

    void assignUnits(Long managerId, List<Long> unitIds);
}