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

    private final WorkOrderTimeLogService timeLogService;

    public WorkOrderServiceImpl(WorkOrderRepository workOrderRepository,
                                WorkOrderTimeLogService timeLogService) {
        this.workOrderRepository = workOrderRepository;
        this.timeLogService = timeLogService;
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

        Long previousUnitId = existing.getUnitId();

        dto.setId(id);

        if (isBlank(dto.getStatus())) {
            dto.setStatus(existing.getStatus());
        }

        dto.setCreatedDate(existing.getCreatedDate());
        dto.setCreatedByUserId(existing.getCreatedByUserId());

        if (dto.getDueDate() == null) {
            dto.setDueDate(existing.getDueDate());
        }

        if (STATUS_COMPLETED.equalsIgnoreCase(dto.getStatus())) {
            dto.setCompletedDate(existing.getCompletedDate() != null
                    ? existing.getCompletedDate()
                    : LocalDateTime.now());
        } else {
            dto.setCompletedDate(null);
        }

        WorkOrderDto saved = workOrderRepository.save(dto);

        if (previousUnitId != null
                && saved.getUnitId() != null
                && !previousUnitId.equals(saved.getUnitId())) {
            timeLogService.syncUnitForWorkOrder(saved.getId(), saved.getUnitId());
        }

        return saved;
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
    public WorkOrderDto changeStatusWithNotes(Long id, String status, String notes) {
        WorkOrderDto existing = workOrderRepository.get(id)
                .orElseThrow(() -> new RuntimeException("Work order not found"));

        existing.setStatus(status);

        if (notes != null && !notes.trim().isEmpty()) {
            existing.setNotes(notes.trim());
        }

        if (STATUS_COMPLETED.equalsIgnoreCase(status)) {
            existing.setCompletedDate(LocalDateTime.now());
        }

        if (STATUS_CANCELLED.equalsIgnoreCase(status)) {
            existing.setCompletedDate(null);
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