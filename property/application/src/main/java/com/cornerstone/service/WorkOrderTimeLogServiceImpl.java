package com.cornerstone.service;

import com.cornerstone.dto.WorkOrderTimeLogDto;
import com.cornerstone.repository.WorkOrderTimeLogRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkOrderTimeLogServiceImpl implements WorkOrderTimeLogService {

    private final WorkOrderTimeLogRepository repository;

    public WorkOrderTimeLogServiceImpl(WorkOrderTimeLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<WorkOrderTimeLogDto> getByWorkOrderId(Long workOrderId) {
        return repository.getByWorkOrderId(workOrderId);
    }

    @Override
    public List<WorkOrderTimeLogDto> getAccessibleUnitLogsBetween(LocalDate fromDate, LocalDate toDate) {
        return repository.getAccessibleUnitLogsBetween(fromDate, toDate);
    }

    @Override
    @Transactional
    public WorkOrderTimeLogDto create(WorkOrderTimeLogDto dto) {

        if (dto.getWorkDate() == null) {
            throw new IllegalArgumentException("Work date is required.");
        }

        if (dto.getStartTime() == null) {
            throw new IllegalArgumentException("Start time is required.");
        }

        if (dto.getEndTime() == null) {
            throw new IllegalArgumentException("End time is required.");
        }

        if (!dto.getEndTime().isAfter(dto.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time. Please check AM/PM or the selected hours.");
        }

        long minutes = Duration.between(dto.getStartTime(), dto.getEndTime()).toMinutes();

        dto.setMinutesWorked((int) minutes);

        return repository.save(dto);
    }

    @Override
    public int getTotalMinutesByWorkOrderId(Long workOrderId) {
        return getByWorkOrderId(workOrderId)
                .stream()
                .map(WorkOrderTimeLogDto::getMinutesWorked)
                .filter(minutes -> minutes != null)
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public Map<String, Integer> getAccessibleUnitTotals(LocalDate fromDate, LocalDate toDate) {

        Map<String, Integer> totals = new LinkedHashMap<>();
        totals.put("146-1", 0);
        totals.put("146-2", 0);
        totals.put("146-3", 0);
        totals.put("146-4", 0);

        List<WorkOrderTimeLogDto> logs = getAccessibleUnitLogsBetween(fromDate, toDate);

        for (WorkOrderTimeLogDto log : logs) {
            String unitNumber = log.getUnitNumber();

            if (unitNumber == null || !totals.containsKey(unitNumber)) {
                continue;
            }

            int minutes = log.getMinutesWorked() == null ? 0 : log.getMinutesWorked();

            totals.put(unitNumber, totals.get(unitNumber) + minutes);
        }

        return totals;
    }

    @Override
    public String formatMinutes(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;

        return hours + "h " + remainingMinutes + "m";
    }

    @Override
    public double toBillableHours(int minutes) {
        return Math.round((minutes / 60.0) * 100.0) / 100.0;
    }

    @Override
    @Transactional
    public void syncUnitForWorkOrder(Long workOrderId, Long unitId) {
        if (workOrderId == null || unitId == null) {
            return;
        }

        repository.updateUnitByWorkOrderId(workOrderId, unitId);
    }
}