package com.cornerstone.repository;

import com.cornerstone.dto.WorkOrderTimeLogDto;

import java.time.LocalDate;
import java.util.List;

public interface WorkOrderTimeLogRepository {

    List<WorkOrderTimeLogDto> getByWorkOrderId(Long workOrderId);

    List<WorkOrderTimeLogDto> getAccessibleUnitLogsBetween(LocalDate fromDate, LocalDate toDate);

    WorkOrderTimeLogDto save(WorkOrderTimeLogDto dto);

    void updateUnitByWorkOrderId(Long workOrderId, Long unitId);
}