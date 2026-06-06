package com.cornerstone.controller;

import com.cornerstone.dto.ManagerDto;
import com.cornerstone.dto.UnitDto;
import com.cornerstone.service.LeaseService;
import com.cornerstone.service.ManagerService;
import com.cornerstone.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerService managerService;
    private final UnitService unitService;
    private final LeaseService leaseService;

    public ManagerController(ManagerService managerService,
                             UnitService unitService,
                             LeaseService leaseService) {
        this.managerService = managerService;
        this.unitService = unitService;
        this.leaseService = leaseService;
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

        ManagerDto manager = managerService.get(id)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        model.addAttribute("manager", manager);
        model.addAttribute("units", getAssignableUnits(manager));

        return "managers/assign-units";
    }

    @PostMapping("/{id}/assign-units")
    public String assignUnits(@PathVariable("id") Long id,
                              @RequestParam(name = "unitIds", required = false) List<Long> unitIds,
                              Model model) {

        try {
            managerService.assignUnits(id, unitIds);
            return "redirect:/managers";

        } catch (RuntimeException ex) {
            ManagerDto manager = managerService.get(id)
                    .orElseThrow(() -> new RuntimeException("Manager not found"));

            model.addAttribute("manager", manager);
            model.addAttribute("units", getAssignableUnits(manager));
            model.addAttribute("assignError", ex.getMessage());

            return "managers/assign-units";
        }
    }

    private List<UnitDto> getAssignableUnits(ManagerDto manager) {

        Set<Long> managerUnitIds = new HashSet<>(manager.getUnitIds());
        Set<Long> activeLeaseUnitIds = new HashSet<>(leaseService.getActiveUnitIds());

        return unitService.getAll()
                .stream()
                .filter(unit -> unit.getId() != null)
                .filter(unit ->
                        managerUnitIds.contains(unit.getId())
                                || !activeLeaseUnitIds.contains(unit.getId())
                )
                .toList();
    }
}