package com.cornerstone.service;

import com.cornerstone.dto.WorkOrderTimeLogDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface WorkOrderTimeLogService {

    List<WorkOrderTimeLogDto> getByWorkOrderId(Long workOrderId);

    List<WorkOrderTimeLogDto> getAccessibleUnitLogsBetween(LocalDate fromDate, LocalDate toDate);

    WorkOrderTimeLogDto create(WorkOrderTimeLogDto dto);

    int getTotalMinutesByWorkOrderId(Long workOrderId);

    Map<String, Integer> getAccessibleUnitTotals(LocalDate fromDate, LocalDate toDate);

    String formatMinutes(int minutes);

    double toBillableHours(int minutes);

    void syncUnitForWorkOrder(Long workOrderId, Long unitId);
}