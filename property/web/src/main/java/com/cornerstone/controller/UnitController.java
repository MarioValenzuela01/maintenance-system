package com.cornerstone.controller;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.dto.UnitDto;
import com.cornerstone.service.LeaseService;
import com.cornerstone.service.ManagerService;
import com.cornerstone.service.UnitMaintenanceHistoryService;
import com.cornerstone.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;
    private final LeaseService leaseService;
    private final ManagerService managerService;
    private final UnitMaintenanceHistoryService historyService;

    public UnitController(UnitService unitService,
                          LeaseService leaseService,
                          ManagerService managerService,
                          UnitMaintenanceHistoryService historyService) {
        this.unitService = unitService;
        this.leaseService = leaseService;
        this.managerService = managerService;
        this.historyService = historyService;
    }

    // --- LIST ---
    @GetMapping
    public String list(@RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "keyword", required = false) String keyword,
                       Model model) {

        prepareUnitsTableModel(page, keyword, model);
        model.addAttribute("keyword", keyword);

        return "units/list";
    }

    // --- TABLE FRAGMENT FOR AJAX SEARCH / PAGINATION ---
    @GetMapping("/table")
    public String table(@RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "keyword", required = false) String keyword,
                        Model model) {

        prepareUnitsTableModel(page, keyword, model);
        model.addAttribute("keyword", keyword);

        return "units/list :: unitsTable";
    }

    // --- CREATE ---
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

    // --- UPDATE ---
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
    public String editSubmit(@PathVariable("id") Long id,
                             @ModelAttribute("unit") UnitDto unit) {
        unitService.update(id, unit);
        return "redirect:/units/details/" + id;
    }

    // --- DELETE ---
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

    // --- DETAILS BY ID ---
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        var unit = unitService.get(id);

        if (unit.isEmpty()) {
            return "redirect:/units";
        }

        var unitDto = unit.get();

        var activeLease = leaseService.getActiveLeaseByUnitId(unitDto.getId());
        List<Long> managedUnitIds = managerService.getManagedUnitIds();

        boolean isManaged = managedUnitIds.contains(unitDto.getId());

        String status;

        if (activeLease.isPresent()) {
            status = "Occupied";
        } else if (isManaged) {
            status = "Managed";
        } else {
            status = "Available";
        }

        model.addAttribute("unit", unitDto);
        model.addAttribute("statusCalculated", status);
        model.addAttribute("activeLease", activeLease.orElse(null));

        return "units/details";
    }

    // --- DETAILS BY UNIT NUMBER ---
    @GetMapping("/details/by-number/{unitNumber}")
    public String detailsByUnitNumber(@PathVariable("unitNumber") String unitNumber,
                                      Model model) {
        var unit = unitService.getByUnitNumber(unitNumber);

        if (unit.isEmpty()) {
            return "redirect:/units";
        }

        var unitDto = unit.get();

        var activeLease = leaseService.getActiveLeaseByUnitId(unitDto.getId());
        List<Long> managedUnitIds = managerService.getManagedUnitIds();

        boolean isManaged = managedUnitIds.contains(unitDto.getId());

        String status;

        if (activeLease.isPresent()) {
            status = "Occupied";
        } else if (isManaged) {
            status = "Managed";
        } else {
            status = "Available";
        }

        model.addAttribute("unit", unitDto);
        model.addAttribute("statusCalculated", status);
        model.addAttribute("activeLease", activeLease.orElse(null));

        return "units/details";
    }

    // --- STANDALONE MAP PAGE ---
    @GetMapping("/map")
    public String map() {
        return "units/map";
    }

    // --- UNIT MAINTENANCE HISTORY ---
    @GetMapping("/{id}/history")
    public String maintenanceHistory(@PathVariable("id") Long id,
                                     @RequestParam(name = "keyword", required = false) String keyword,
                                     @RequestParam(name = "category", required = false) String category,
                                     Model model) {

        var unit = unitService.get(id);

        if (unit.isEmpty()) {
            return "redirect:/units";
        }

        var unitDto = unit.get();

        var historyList = historyService.getByUnitId(unitDto.getId());

        if (category != null && !category.isBlank()) {
            historyList = historyList.stream()
                    .filter(h -> h.getCategory() != null &&
                            h.getCategory().equalsIgnoreCase(category))
                    .toList();
        }

        if (keyword != null && !keyword.isBlank()) {
            String search = keyword.toLowerCase(Locale.ROOT).trim();

            historyList = historyList.stream()
                    .filter(h ->
                            contains(h.getItemName(), search) ||
                                    contains(h.getCategory(), search) ||
                                    contains(h.getNotes(), search) ||
                                    contains(h.getTenantNameAtTime(), search)
                    )
                    .toList();
        }

        model.addAttribute("unit", unitDto);
        model.addAttribute("historyList", historyList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);

        return "units/history";
    }

    // =========================================================
    // PRIVATE HELPERS
    // =========================================================

    private void prepareUnitsTableModel(int page, String keyword, Model model) {
        List<UnitDto> units = unitService.getAll();
        List<LeaseDto> leases = leaseService.getAll();
        List<Long> managedUnitIds = managerService.getManagedUnitIds();

        calculateUnitStatuses(units, leases, managedUnitIds);

        units = filterUnits(units, keyword);

        int pageSize = 15;
        int totalMatchingUnits = units.size();
        int totalPages = (int) Math.ceil((double) totalMatchingUnits / pageSize);

        if (page < 0) {
            page = 0;
        }

        if (totalPages > 0 && page >= totalPages) {
            page = totalPages - 1;
        }

        int start = page * pageSize;
        int end = Math.min(start + pageSize, totalMatchingUnits);

        List<UnitDto> pagedUnits = new ArrayList<>();

        if (!units.isEmpty() && start < units.size()) {
            pagedUnits = units.subList(start, end);
        }

        model.addAttribute("units", pagedUnits);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalMatchingUnits", totalMatchingUnits);
    }

    private void calculateUnitStatuses(List<UnitDto> units,
                                       List<LeaseDto> leases,
                                       List<Long> managedUnitIds) {

        for (UnitDto unitDto : units) {
            boolean isOccupied = leases.stream()
                    .anyMatch(lease ->
                            lease.getUnitId() != null &&
                                    lease.getUnitId().equals(unitDto.getId()) &&
                                    lease.getEndDate() == null
                    );

            boolean isManaged = managedUnitIds.contains(unitDto.getId());

            if (isOccupied) {
                unitDto.setStatus("Occupied");
            } else if (isManaged) {
                unitDto.setStatus("Managed");
            } else {
                unitDto.setStatus("Available");
            }
        }
    }

    private List<UnitDto> filterUnits(List<UnitDto> units, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return units;
        }

        String search = keyword.toLowerCase(Locale.ROOT).trim();

        return units.stream()
                .filter(unit ->
                        contains(unit.getUnitNumber(), search) ||
                                contains(unit.getDisplayName(), search) ||
                                contains(unit.getAddress(), search) ||
                                contains(unit.getStatus(), search)
                )
                .toList();
    }

    private boolean contains(String value, String search) {
        return value != null &&
                value.toLowerCase(Locale.ROOT).contains(search);
    }
}