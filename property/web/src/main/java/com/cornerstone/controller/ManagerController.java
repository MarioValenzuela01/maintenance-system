package com.cornerstone.controller;

import com.cornerstone.dto.ManagerDto;
import com.cornerstone.service.ManagerService;
import com.cornerstone.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerService managerService;
    private final UnitService unitService;

    public ManagerController(ManagerService managerService, UnitService unitService) {
        this.managerService = managerService;
        this.unitService = unitService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("managers", managerService.getAll());
        return "managers/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("manager", new ManagerDto());
        return "managers/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ManagerDto managerDto) {
        managerService.create(managerDto);
        return "redirect:/managers";
    }

    @GetMapping("/{id}/assign-units")
    public String assignUnitsForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("manager", managerService.get(id).orElseThrow());
        model.addAttribute("units", unitService.getAll());
        return "managers/assign-units";
    }

    @PostMapping("/{id}/assign-units")
    public String assignUnits(
            @PathVariable("id") Long id,
            @RequestParam(name = "unitIds", required = false) List<Long> unitIds
    ) {
        managerService.assignUnits(id, unitIds);
        return "redirect:/managers";
    }
}