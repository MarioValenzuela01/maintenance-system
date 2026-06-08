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

    private final WorkOrderTimeLogService timeLogService;

    public WorkOrderReportController(WorkOrderTimeLogService timeLogService) {
        this.timeLogService = timeLogService;
    }

    @GetMapping("/accessible-units-hours")
    public String accessibleUnitsHours(
            @RequestParam(name = "period", defaultValue = "thisMonth") String period,
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

        Map<String, Integer> totals = timeLogService.getAccessibleUnitTotals(fromDate, toDate);
        List<WorkOrderTimeLogDto> logs = timeLogService.getAccessibleUnitLogsBetween(fromDate, toDate);

        List<Map<String, Object>> summaryRows = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : totals.entrySet()) {
            int minutes = entry.getValue();

            summaryRows.add(Map.of(
                    "unitNumber", entry.getKey(),
                    "minutes", minutes,
                    "formattedTime", timeLogService.formatMinutes(minutes),
                    "billableHours", timeLogService.toBillableHours(minutes)
            ));
        }

        int grandTotalMinutes = totals.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        model.addAttribute("period", period);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("summaryRows", summaryRows);
        model.addAttribute("logs", logs);
        model.addAttribute("grandTotalTime", timeLogService.formatMinutes(grandTotalMinutes));
        model.addAttribute("grandTotalBillableHours", timeLogService.toBillableHours(grandTotalMinutes));

        return "work-orders/reports/accessible-units-hours";
    }
}