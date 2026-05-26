package com.cornerstone.controller;

import com.cornerstone.dto.UnitMaintenanceHistoryDto;
import com.cornerstone.service.LeaseService;
import com.cornerstone.service.UnitMaintenanceHistoryService;
import com.cornerstone.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/unit-history")
public class UnitMaintenanceHistoryController {

    private final UnitMaintenanceHistoryService service;
    private final LeaseService leaseService;
    private final UnitService unitService;

    public UnitMaintenanceHistoryController(UnitMaintenanceHistoryService service,
                                            LeaseService leaseService,
                                            UnitService unitService) {
        this.service = service;
        this.leaseService = leaseService;
        this.unitService = unitService;
    }

    @GetMapping("/create/{unitId}")
    public String create(@PathVariable("unitId") Long unitId, Model model) {
        model.addAttribute("history", new UnitMaintenanceHistoryDto().setUnitId(unitId));
        return "unit-history/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("history") UnitMaintenanceHistoryDto dto) {

        var activeLease = leaseService.getActiveLeaseByUnitId(dto.getUnitId());

        if (activeLease.isPresent()) {
            dto.setTenantIdAtTime(activeLease.get().getTenantId());
            dto.setTenantNameAtTime(activeLease.get().getTenantName());
        } else {
            dto.setTenantNameAtTime("No tenant at this time");
        }

        service.create(dto);
        return "redirect:/units/" + dto.getUnitId() + "/history";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        UnitMaintenanceHistoryDto dto = service.get(id)
                .orElseThrow(() -> new RuntimeException("Maintenance record not found"));

        model.addAttribute("history", dto);
        return "unit-history/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,
                       @ModelAttribute("history") UnitMaintenanceHistoryDto dto) {
        service.update(id, dto);
        return "redirect:/units/" + dto.getUnitId() + "/history";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        UnitMaintenanceHistoryDto dto = service.get(id)
                .orElseThrow(() -> new RuntimeException("Maintenance record not found"));

        model.addAttribute("history", dto);
        return "unit-history/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteConfirmed(@PathVariable("id") Long id,
                                  @RequestParam("unitId") Long unitId) {
        service.delete(id);
        return "redirect:/units/" + unitId + "/history";
    }

    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String keyword,
                       @RequestParam(name = "category", required = false) String category,
                       Model model) {

        List<UnitMaintenanceHistoryDto> historyList = service.getAll();

        for (UnitMaintenanceHistoryDto item : historyList) {
            unitService.get(item.getUnitId())
                    .ifPresent(unit -> item.setUnitNumber(unit.getUnitNumber()));
        }

        if (category != null && !category.isBlank()) {
            historyList = historyList.stream()
                    .filter(h -> h.getCategory() != null &&
                            h.getCategory().equalsIgnoreCase(category))
                    .toList();
        }

        if (keyword != null && !keyword.isBlank()) {
            String search = keyword.toLowerCase();

            historyList = historyList.stream()
                    .filter(h ->
                            (h.getUnitNumber() != null && h.getUnitNumber().toLowerCase().contains(search)) ||
                                    (h.getItemName() != null && h.getItemName().toLowerCase().contains(search)) ||
                                    (h.getCategory() != null && h.getCategory().toLowerCase().contains(search)) ||
                                    (h.getNotes() != null && h.getNotes().toLowerCase().contains(search)) ||
                                    (h.getTenantNameAtTime() != null && h.getTenantNameAtTime().toLowerCase().contains(search))
                    )
                    .toList();
        }

        model.addAttribute("historyList", historyList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);

        return "unit-history/list";
    }
}