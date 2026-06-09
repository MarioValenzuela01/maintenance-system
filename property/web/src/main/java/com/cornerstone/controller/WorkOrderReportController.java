package com.cornerstone.controller;

import com.cornerstone.dto.WorkOrderTimeLogDto;
import com.cornerstone.service.WorkOrderTimeLogService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/work-orders/reports")
public class WorkOrderReportController {

    private static final List<String> ACCESSIBLE_UNITS = List.of(
            "146-1",
            "146-2",
            "146-3",
            "146-4"
    );

    private final WorkOrderTimeLogService timeLogService;

    public WorkOrderReportController(WorkOrderTimeLogService timeLogService) {
        this.timeLogService = timeLogService;
    }

    @GetMapping("/accessible-units-hours")
    public String accessibleUnitsHours(
            @RequestParam(name = "period", defaultValue = "thisMonth") String period,
            @RequestParam(name = "unitNumber", required = false) String unitNumber,
            @RequestParam(name = "fromDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fromDate,
            @RequestParam(name = "toDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate toDate,
            Model model) {

        LocalDate today = LocalDate.now();

        if ("thisWeek".equalsIgnoreCase(period)) {
            fromDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            toDate = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        } else if ("lastWeek".equalsIgnoreCase(period)) {
            LocalDate lastWeek = today.minusWeeks(1);
            fromDate = lastWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            toDate = lastWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        } else if ("lastMonth".equalsIgnoreCase(period)) {
            LocalDate lastMonth = today.minusMonths(1);
            fromDate = lastMonth.withDayOfMonth(1);
            toDate = lastMonth.withDayOfMonth(lastMonth.lengthOfMonth());
        } else if ("custom".equalsIgnoreCase(period)) {
            if (fromDate == null) {
                fromDate = today.withDayOfMonth(1);
            }

            if (toDate == null) {
                toDate = today;
            }
        } else {
            period = "thisMonth";
            fromDate = today.withDayOfMonth(1);
            toDate = today.withDayOfMonth(today.lengthOfMonth());
        }

        String selectedUnitNumber = normalizeUnitNumber(unitNumber);

        List<WorkOrderTimeLogDto> logs = timeLogService.getAccessibleUnitLogsBetween(fromDate, toDate);

        if (selectedUnitNumber != null) {
            logs = logs.stream()
                    .filter(log -> selectedUnitNumber.equalsIgnoreCase(log.getUnitNumber()))
                    .toList();
        }

        Map<String, Integer> totals = timeLogService.getAccessibleUnitTotals(fromDate, toDate);

        List<Map<String, Object>> summaryRows = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : totals.entrySet()) {
            String currentUnitNumber = entry.getKey();

            if (selectedUnitNumber != null && !selectedUnitNumber.equalsIgnoreCase(currentUnitNumber)) {
                continue;
            }

            int minutes;

            if (selectedUnitNumber == null) {
                minutes = entry.getValue();
            } else {
                minutes = logs.stream()
                        .filter(log -> currentUnitNumber.equalsIgnoreCase(log.getUnitNumber()))
                        .map(WorkOrderTimeLogDto::getMinutesWorked)
                        .filter(value -> value != null)
                        .mapToInt(Integer::intValue)
                        .sum();
            }

            summaryRows.add(Map.of(
                    "unitNumber", currentUnitNumber,
                    "minutes", minutes,
                    "formattedTime", timeLogService.formatMinutes(minutes),
                    "billableHours", timeLogService.toBillableHours(minutes)
            ));
        }

        int grandTotalMinutes = logs.stream()
                .map(WorkOrderTimeLogDto::getMinutesWorked)
                .filter(value -> value != null)
                .mapToInt(Integer::intValue)
                .sum();

        model.addAttribute("period", period);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("unitNumber", selectedUnitNumber);
        model.addAttribute("accessibleUnits", ACCESSIBLE_UNITS);

        model.addAttribute("summaryRows", summaryRows);
        model.addAttribute("logs", logs);
        model.addAttribute("grandTotalTime", timeLogService.formatMinutes(grandTotalMinutes));
        model.addAttribute("grandTotalBillableHours", timeLogService.toBillableHours(grandTotalMinutes));

        return "work-orders/reports/accessible-units-hours";
    }

    private String normalizeUnitNumber(String unitNumber) {
        if (unitNumber == null || unitNumber.isBlank()) {
            return null;
        }

        String value = unitNumber.trim();

        if ("ALL".equalsIgnoreCase(value)) {
            return null;
        }

        if (!ACCESSIBLE_UNITS.contains(value)) {
            return null;
        }

        return value;
    }
}