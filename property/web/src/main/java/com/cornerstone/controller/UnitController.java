package com.cornerstone.controller;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.dto.UnitDto;
import com.cornerstone.service.LeaseService;
import com.cornerstone.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;
    private final LeaseService leaseService;

    public UnitController(UnitService unitService, LeaseService leaseService) {
        this.unitService = unitService;
        this.leaseService = leaseService;

    }

    // --- LEER (Read) ---
    @GetMapping
    public String list(@RequestParam(name="page", defaultValue = "0") int page, Model model) {
        List<UnitDto> units = unitService.getAll();
        List<LeaseDto> leases = leaseService.getAll();

        for (UnitDto unitDto : units) {
            boolean isOccupied = leases.stream()
                    .anyMatch(lease -> lease.getUnitId().equals(unitDto.getId()) && lease.getEndDate() == null);

            unitDto.setStatus(isOccupied ? "Occupied" : "Not Occupied");
        }

        // --- NUEVA LÓGICA DE AGRUPACIÓN (15 registros) ---

        int pageSize = 10;
        int totalPages = (int) Math.ceil((double) units.size() / pageSize);

        if (page < 0) {
            page = 0;
        }

        if (totalPages > 0 && page >= totalPages) {
            page = totalPages - 1;
        }

        int start = page * pageSize;
        int end = Math.min(start + pageSize, units.size());

        List<UnitDto> pagedUnits = new ArrayList<>();

        if (!units.isEmpty() && start < units.size()) {
            pagedUnits = units.subList(start, end);
        }

        model.addAttribute("units", pagedUnits);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "units/list";
    }

    // --- CREAR (Create) ---
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("unit", new UnitDto());
        return "units/create";
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute("unit") UnitDto unit) {
        unitService.create(unit);
        return "redirect:/units";
    }

    // --- ACTUALIZAR (Update) ---
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Optional<UnitDto> unit = unitService.get(id);
        if (unit.isPresent()) {
            model.addAttribute("unit", unit.get());
            return "units/edit";
        }
        return "redirect:/units";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable("id") Long id, @ModelAttribute("unit") UnitDto unit) {
        unitService.update(id, unit);
        return "redirect:/units/details/" + id;
    }

    // --- ELIMINAR (Delete) ---
    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable("id") Long id, Model model) {
        Optional<UnitDto> unit = unitService.get(id);
        if (unit.isPresent()) {
            model.addAttribute("unit", unit.get());
            return "units/delete";
        }
        return "redirect:/units";
    }

    @PostMapping("/delete/{id}")
    public String deleteSubmit(@PathVariable("id") Long id) {
        unitService.delete(id);
        return "redirect:/units";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        var unit = unitService.get(id);

        if (unit.isEmpty()) {
            return "redirect:/units";
        }

        var unitDto = unit.get();

        var activeLease = leaseService.getActiveLeaseByUnitId(unitDto.getId());

        String status = activeLease.isPresent() ? "Occupied" : "Available";

        model.addAttribute("unit", unitDto);
        model.addAttribute("statusCalculated", status);
        model.addAttribute("activeLease", activeLease.orElse(null));

        return "units/details";
    }

    @GetMapping("/details/by-number/{unitNumber}")
    public String detailsByUnitNumber(@PathVariable("unitNumber") String unitNumber, Model model) {

        var unit = unitService.getByUnitNumber(unitNumber);

        if (unit.isEmpty()) {
            return "redirect:/units";
        }

        var unitDto = unit.get();

        // 🔥 SOLO UNA VEZ
        var activeLease = leaseService.getActiveLeaseByUnitId(unitDto.getId());

        String status = activeLease.isPresent() ? "Occupied" : "Available";

        model.addAttribute("unit", unitDto);
        model.addAttribute("statusCalculated", status);
        model.addAttribute("activeLease", activeLease.orElse(null)); // 🔥 CLAVE

        return "units/details";
    }

    @GetMapping("/table")
    public String table(@RequestParam(name="page", defaultValue = "0") int page, Model model) {
        List<UnitDto> units = unitService.getAll();
        List<LeaseDto> leases = leaseService.getAll();

        for (UnitDto unitDto : units) {
            boolean isOccupied = leases.stream()
                    .anyMatch(lease -> lease.getUnitId().equals(unitDto.getId()) && lease.getEndDate() == null);

            unitDto.setStatus(isOccupied ? "Occupied" : "Not Occupied");
        }

        int pageSize = 10;
        int totalPages = (int) Math.ceil((double) units.size() / pageSize);

        if (page < 0) {
            page = 0;
        }

        if (totalPages > 0 && page >= totalPages) {
            page = totalPages - 1;
        }

        int start = page * pageSize;
        int end = Math.min(start + pageSize, units.size());

        List<UnitDto> pagedUnits = new ArrayList<>();

        if (!units.isEmpty() && start < units.size()) {
            pagedUnits = units.subList(start, end);
        }

        model.addAttribute("units", pagedUnits);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "units/list :: unitsTable";
    }

    @GetMapping("/map")
    public String map() {
        return "units/map";
    }



}
