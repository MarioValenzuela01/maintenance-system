package com.cornerstone.repository;

import com.cornerstone.dto.WorkOrderDto;

import java.util.List;
import java.util.Optional;

public interface WorkOrderRepository {

    List<WorkOrderDto> getAll();

    List<WorkOrderDto> getByAssignedUsername(String username);

    Optional<WorkOrderDto> get(Long id);

    WorkOrderDto save(WorkOrderDto dto);

    void delete(Long id);
}