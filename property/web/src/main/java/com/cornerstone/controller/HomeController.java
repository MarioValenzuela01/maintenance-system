package com.cornerstone.controller;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.dto.TenantDto;
import com.cornerstone.dto.UnitDto;
import com.cornerstone.dto.UnitMaintenanceHistoryDto;
import com.cornerstone.service.LeaseService;
import com.cornerstone.service.ManagerService;
import com.cornerstone.service.TenantService;
import com.cornerstone.service.UnitMaintenanceHistoryService;
import com.cornerstone.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
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

        LocalDate today = LocalDate.now();
        LocalDate next90Days = today.plusDays(90);

        List<UnitDto> units = unitService.getAll();
        List<TenantDto> tenants = tenantService.getAll();
        List<LeaseDto> leases = leaseService.getAll();
        List<UnitMaintenanceHistoryDto> historyList = historyService.getAll();

        /*
         * En tu sistema estamos usando managedUnitIds como unidades internas /
         * no rentables, por ejemplo bodegas o espacios bajo responsabilidad
         * de Cornerstone que no deberían afectar la ocupación.
         */
        List<Long> internalUnitIds = managerService.getManagedUnitIds();

        List<LeaseDto> activeLeases = leases.stream()
                .filter(lease -> lease.getEndDate() == null)
                .toList();

        Set<Long> occupiedUnitIds = activeLeases.stream()
                .map(LeaseDto::getUnitId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        long totalUnits = units.size();

        long internalUnits = units.stream()
                .filter(unit -> internalUnitIds.contains(unit.getId()))
                .count();

        long rentableUnits = units.stream()
                .filter(unit -> !internalUnitIds.contains(unit.getId()))
                .count();

        long occupiedRentableUnits = units.stream()
                .filter(unit -> !internalUnitIds.contains(unit.getId()))
                .filter(unit -> occupiedUnitIds.contains(unit.getId()))
                .count();

        long availableForRent = units.stream()
                .filter(unit -> !internalUnitIds.contains(unit.getId()))
                .filter(unit -> !occupiedUnitIds.contains(unit.getId()))
                .count();

        int occupancyRate = rentableUnits == 0
                ? 0
                : (int) Math.round((occupiedRentableUnits * 100.0) / rentableUnits);

        int vacancyRate = rentableUnits == 0
                ? 0
                : (int) Math.round((availableForRent * 100.0) / rentableUnits);

        long maintenanceThisMonth = historyList.stream()
                .filter(item -> item.getCompletedDate() != null)
                .filter(item -> item.getCompletedDate().getMonth() == today.getMonth())
                .filter(item -> item.getCompletedDate().getYear() == today.getYear())
                .count();

        long leasesEndingSoon = leases.stream()
                .filter(lease -> lease.getEndDate() != null)
                .filter(lease -> !lease.getEndDate().isBefore(today))
                .filter(lease -> !lease.getEndDate().isAfter(next90Days))
                .count();

        long tenantsMissingEmail = tenants.stream()
                .filter(tenant -> isBlank(tenant.getEmail()))
                .count();

        long tenantsMissingEmergencyContact = tenants.stream()
                .filter(tenant ->
                        isBlank(tenant.getEmergencyContactName())
                                || isBlank(tenant.getEmergencyContactPhone()))
                .count();

        long leasesMissingStartDate = leases.stream()
                .filter(lease -> lease.getStartDate() == null)
                .count();

        long totalDataIssues = tenantsMissingEmail
                + tenantsMissingEmergencyContact
                + leasesMissingStartDate;

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

        List<Map<String, Object>> topMaintenanceUnits = historyList.stream()
                .collect(Collectors.groupingBy(
                        this::getMaintenanceUnitLabel,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(entry -> {
                    Map<String, Object> row = new HashMap<>();
                    row.put("unit", entry.getKey());
                    row.put("records", entry.getValue());
                    return row;
                })
                .toList();

        model.addAttribute("totalUnits", totalUnits);
        model.addAttribute("rentableUnits", rentableUnits);
        model.addAttribute("internalUnits", internalUnits);

        model.addAttribute("occupiedRentableUnits", occupiedRentableUnits);
        model.addAttribute("availableForRent", availableForRent);

        model.addAttribute("occupancyRate", occupancyRate);
        model.addAttribute("vacancyRate", vacancyRate);

        model.addAttribute("maintenanceThisMonth", maintenanceThisMonth);
        model.addAttribute("leasesEndingSoon", leasesEndingSoon);

        model.addAttribute("tenantsMissingEmail", tenantsMissingEmail);
        model.addAttribute("tenantsMissingEmergencyContact", tenantsMissingEmergencyContact);
        model.addAttribute("leasesMissingStartDate", leasesMissingStartDate);
        model.addAttribute("totalDataIssues", totalDataIssues);

        model.addAttribute("recentHistory", recentHistory);
        model.addAttribute("recentActiveLeases", recentActiveLeases);
        model.addAttribute("topMaintenanceUnits", topMaintenanceUnits);

        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboardRedirect() {
        return "redirect:/";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String getMaintenanceUnitLabel(UnitMaintenanceHistoryDto item) {
        if (!isBlank(item.getUnitDisplayName())) {
            return item.getUnitDisplayName();
        }

        if (!isBlank(item.getUnitNumber())) {
            return "Unit " + item.getUnitNumber();
        }

        return "Unknown Unit";
    }

    @GetMapping("/dashboard/data-quality")
    public String dataQualityDetails(
            @RequestParam(name = "type", defaultValue = "all") String type,
            Model model) {

        List<TenantDto> tenants = tenantService.getAll();
        List<LeaseDto> leases = leaseService.getAll();

        List<Map<String, Object>> tenantIssues = tenants.stream()
                .map(tenant -> {
                    Map<String, Object> row = new HashMap<String, Object>();

                    boolean missingEmail = isBlank(tenant.getEmail());
                    boolean missingEmergencyName = isBlank(tenant.getEmergencyContactName());
                    boolean missingEmergencyPhone = isBlank(tenant.getEmergencyContactPhone());

                    if ("email".equalsIgnoreCase(type) && !missingEmail) {
                        return null;
                    }

                    if ("emergency".equalsIgnoreCase(type)
                            && !missingEmergencyName
                            && !missingEmergencyPhone) {
                        return null;
                    }

                    if ("lease-start".equalsIgnoreCase(type)) {
                        return null;
                    }

                    if (!"all".equalsIgnoreCase(type)
                            && !"email".equalsIgnoreCase(type)
                            && !"emergency".equalsIgnoreCase(type)) {
                        return null;
                    }

                    if (!missingEmail && !missingEmergencyName && !missingEmergencyPhone) {
                        return null;
                    }

                    row.put("id", tenant.getId());
                    row.put("name", tenant.getFirstName() + " " + tenant.getLastName());
                    row.put("type", "Tenant");

                    List<String> missingFields = new ArrayList<>();

                    if ("email".equalsIgnoreCase(type)) {
                        missingFields.add("Email");
                    } else if ("emergency".equalsIgnoreCase(type)) {
                        if (missingEmergencyName) {
                            missingFields.add("Emergency Contact Name");
                        }

                        if (missingEmergencyPhone) {
                            missingFields.add("Emergency Contact Phone");
                        }
                    } else {
                        if (missingEmail) {
                            missingFields.add("Email");
                        }

                        if (missingEmergencyName) {
                            missingFields.add("Emergency Contact Name");
                        }

                        if (missingEmergencyPhone) {
                            missingFields.add("Emergency Contact Phone");
                        }
                    }

                    row.put("missingFields", String.join(", ", missingFields));
                    row.put("detailsUrl", "/tenants/details/" + tenant.getId());

                    return row;
                })
                .filter(Objects::nonNull)
                .toList();

        List<Map<String, Object>> leaseIssues = leases.stream()
                .map(lease -> {
                    if (!"all".equalsIgnoreCase(type) && !"lease-start".equalsIgnoreCase(type)) {
                        return null;
                    }

                    if (lease.getStartDate() != null) {
                        return null;
                    }

                    Map<String, Object> row = new HashMap<String, Object>();

                    row.put("id", lease.getId());
                    row.put("name", lease.getTenantName());
                    row.put("type", "Lease");
                    row.put("unit", lease.getUnitDisplayLabel());
                    row.put("missingFields", "Start Date");
                    row.put("detailsUrl", "/leases/details/" + lease.getId());

                    return row;
                })
                .filter(Objects::nonNull)
                .toList();

        model.addAttribute("tenantIssues", tenantIssues);
        model.addAttribute("leaseIssues", leaseIssues);
        model.addAttribute("type", type);

        return "dashboard/data-quality";
    }
}