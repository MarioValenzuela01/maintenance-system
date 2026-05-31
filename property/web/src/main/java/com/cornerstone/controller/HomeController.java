package com.cornerstone.controller;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.dto.UnitMaintenanceHistoryDto;
import com.cornerstone.dto.UnitDto;
import com.cornerstone.service.LeaseService;
import com.cornerstone.service.ManagerService;
import com.cornerstone.service.TenantService;
import com.cornerstone.service.UnitMaintenanceHistoryService;
import com.cornerstone.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final UnitService unitService;
    private final TenantService tenantService;
    private final LeaseService leaseService;
    private final ManagerService managerService;
    private final UnitMaintenanceHistoryService historyService;

    public HomeController(UnitService unitService,
                          TenantService tenantService,
                          LeaseService leaseService,
                          ManagerService managerService,
                          UnitMaintenanceHistoryService historyService) {
        this.unitService = unitService;
        this.tenantService = tenantService;
        this.leaseService = leaseService;
        this.managerService = managerService;
        this.historyService = historyService;
    }

    @GetMapping("/")
    public String home(Model model) {

        List<UnitDto> units = unitService.getAll();
        List<LeaseDto> leases = leaseService.getAll();
        List<UnitMaintenanceHistoryDto> historyList = historyService.getAll();

        List<LeaseDto> activeLeases = leases.stream()
                .filter(lease -> lease.getEndDate() == null)
                .toList();

        Set<Long> occupiedUnitIds = activeLeases.stream()
                .map(LeaseDto::getUnitId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<Long> managedUnitIds = managerService.getManagedUnitIds();

        long totalUnits = units.size();
        long occupiedUnits = units.stream()
                .filter(unit -> occupiedUnitIds.contains(unit.getId()))
                .count();

        long managedUnits = units.stream()
                .filter(unit -> managedUnitIds.contains(unit.getId()))
                .count();

        long availableUnits = units.stream()
                .filter(unit -> !occupiedUnitIds.contains(unit.getId()))
                .filter(unit -> !managedUnitIds.contains(unit.getId()))
                .count();

        long totalTenants = tenantService.getAll().size();
        long totalManagers = managerService.getAll().size();
        long totalLeases = leases.size();
        long totalHistoryRecords = historyList.size();

        int occupancyRate = totalUnits == 0
                ? 0
                : (int) Math.round((occupiedUnits * 100.0) / totalUnits);

        long endedLeases = leases.stream()
                .filter(lease -> lease.getEndDate() != null)
                .count();

        long maintenanceThisMonth = historyList.stream()
                .filter(item -> item.getCompletedDate() != null)
                .filter(item -> item.getCompletedDate().getMonth() == LocalDate.now().getMonth())
                .filter(item -> item.getCompletedDate().getYear() == LocalDate.now().getYear())
                .count();

        List<UnitMaintenanceHistoryDto> recentHistory = historyList.stream()
                .filter(item -> item.getCompletedDate() != null)
                .sorted(Comparator.comparing(UnitMaintenanceHistoryDto::getCompletedDate).reversed())
                .limit(5)
                .toList();

        List<LeaseDto> recentActiveLeases = activeLeases.stream()
                .filter(lease -> lease.getStartDate() != null)
                .sorted(Comparator.comparing(LeaseDto::getStartDate).reversed())
                .limit(5)
                .toList();

        model.addAttribute("totalUnits", totalUnits);
        model.addAttribute("occupiedUnits", occupiedUnits);
        model.addAttribute("availableUnits", availableUnits);
        model.addAttribute("managedUnits", managedUnits);

        model.addAttribute("totalTenants", totalTenants);
        model.addAttribute("totalManagers", totalManagers);

        model.addAttribute("totalLeases", totalLeases);
        model.addAttribute("activeLeasesCount", activeLeases.size());
        model.addAttribute("endedLeases", endedLeases);

        model.addAttribute("totalHistoryRecords", totalHistoryRecords);
        model.addAttribute("maintenanceThisMonth", maintenanceThisMonth);

        model.addAttribute("occupancyRate", occupancyRate);
        model.addAttribute("recentHistory", recentHistory);
        model.addAttribute("recentActiveLeases", recentActiveLeases);

        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboardRedirect() {
        return "redirect:/";
    }
}