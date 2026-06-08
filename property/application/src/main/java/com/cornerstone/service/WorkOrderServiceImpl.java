package com.cornerstone.service;

import com.cornerstone.dto.WorkOrderDto;
import com.cornerstone.repository.WorkOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    private static final String STATUS_ASSIGNED = "ASSIGNED";
    private static final String STATUS_COMPLETED = "COMPLETED";
    private static final String STATUS_CANCELLED = "CANCELLED";

    private final WorkOrderRepository workOrderRepository;

    public WorkOrderServiceImpl(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    @Override
    public List<WorkOrderDto> getAll() {
        return workOrderRepository.getAll();
    }

    @Override
    public List<WorkOrderDto> getMyOrders(String username) {
        return workOrderRepository.getByAssignedUsername(username);
    }

    @Override
    public Optional<WorkOrderDto> get(Long id) {
        return workOrderRepository.get(id);
    }

    @Override
    public WorkOrderDto create(WorkOrderDto dto) {
        if (isBlank(dto.getStatus())) {
            dto.setStatus(STATUS_ASSIGNED);
        }

        if (isBlank(dto.getPriority())) {
            dto.setPriority("MEDIUM");
        }

        return workOrderRepository.save(dto);
    }

    @Override
    public WorkOrderDto update(Long id, WorkOrderDto dto) {
        WorkOrderDto existing = workOrderRepository.get(id)
                .orElseThrow(() -> new RuntimeException("Work order not found"));

        dto.setId(id);

        if (isBlank(dto.getStatus())) {
            dto.setStatus(existing.getStatus());
        }

        if (STATUS_COMPLETED.equalsIgnoreCase(dto.getStatus())) {
            dto.setCompletedDate(existing.getCompletedDate() != null
                    ? existing.getCompletedDate()
                    : LocalDateTime.now());
        } else {
            dto.setCompletedDate(existing.getCompletedDate());
        }

        dto.setCreatedDate(existing.getCreatedDate());
        dto.setCreatedByUserId(existing.getCreatedByUserId());

        return workOrderRepository.save(dto);
    }

    @Override
    public WorkOrderDto changeStatus(Long id, String status) {
        WorkOrderDto existing = workOrderRepository.get(id)
                .orElseThrow(() -> new RuntimeException("Work order not found"));

        existing.setStatus(status);

        if (STATUS_COMPLETED.equalsIgnoreCase(status)) {
            existing.setCompletedDate(LocalDateTime.now());
        }

        return workOrderRepository.save(existing);
    }

    @Override
    public WorkOrderDto cancel(Long id) {
        return changeStatus(id, STATUS_CANCELLED);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}