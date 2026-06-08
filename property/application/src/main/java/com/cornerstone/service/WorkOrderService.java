package com.cornerstone.service;

import com.cornerstone.dto.WorkOrderDto;

import java.util.List;
import java.util.Optional;

public interface WorkOrderService {

    List<WorkOrderDto> getAll();

    List<WorkOrderDto> getMyOrders(String username);

    Optional<WorkOrderDto> get(Long id);

    WorkOrderDto create(WorkOrderDto dto);

    WorkOrderDto update(Long id, WorkOrderDto dto);

    WorkOrderDto changeStatus(Long id, String status);

    WorkOrderDto cancel(Long id);
}