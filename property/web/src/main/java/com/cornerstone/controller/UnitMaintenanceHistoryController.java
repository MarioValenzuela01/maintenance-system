package com.cornerstone.controller;

import com.cornerstone.dto.UnitMaintenanceHistoryDto;
import com.cornerstone.service.UnitMaintenanceHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/unit-history")
public class UnitMaintenanceHistoryController {

    private final UnitMaintenanceHistoryService service;

    public UnitMaintenanceHistoryController(UnitMaintenanceHistoryService service) {
        this.service = service;
    }

    @GetMapping("/create/{unitId}")
    public String create(@PathVariable("unitId") Long unitId, Model model) {
        model.addAttribute("history", new UnitMaintenanceHistoryDto().setUnitId(unitId));
        return "unit-history/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("history") UnitMaintenanceHistoryDto dto) {
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
}