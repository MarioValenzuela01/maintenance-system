package com.cornerstone.controller;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.dto.PrintableTenantRowDto;
import com.cornerstone.dto.TenantDto;
import com.cornerstone.dto.UnitDto;
import com.cornerstone.service.LeaseService;
import com.cornerstone.service.TenantService;
import com.cornerstone.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class PrintController {

    private final TenantService tenantService;
    private final UnitService unitService;
    private final LeaseService leaseService;

    public PrintController(TenantService tenantService,
                           UnitService unitService,
                           LeaseService leaseService) {
        this.tenantService = tenantService;
        this.unitService = unitService;
        this.leaseService = leaseService;
    }

    @GetMapping("/print/tenants")
    public String printTenants(Model model) {

        List<TenantDto> tenants = tenantService.getAll();
        List<UnitDto> units = unitService.getAll();
        List<LeaseDto> leases = leaseService.getAll();

        Map<Long, TenantDto> tenantById = tenants.stream()
                .filter(tenant -> tenant.getId() != null)
                .collect(Collectors.toMap(
                        TenantDto::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        Map<Long, UnitDto> unitById = units.stream()
                .filter(unit -> unit.getId() != null)
                .collect(Collectors.toMap(
                        UnitDto::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        List<PrintableTenantRowDto> rows = leases.stream()
                .filter(lease -> lease.getEndDate() == null)
                .map(lease -> buildRow(lease, tenantById, unitById))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(
                        PrintableTenantRowDto::getUnitNumber,
                        Comparator.nullsLast(this::compareUnitNumbers)
                ))
                .toList();

        model.addAttribute("rows", rows);
        model.addAttribute("printedDate", LocalDate.now());

        return "print/tenants";
    }

    private PrintableTenantRowDto buildRow(LeaseDto lease,
                                           Map<Long, TenantDto> tenantById,
                                           Map<Long, UnitDto> unitById) {

        TenantDto tenant = tenantById.get(lease.getTenantId());
        UnitDto unit = unitById.get(lease.getUnitId());

        if (tenant == null || unit == null) {
            return null;
        }

        return new PrintableTenantRowDto()
                .setUnitNumber(unit.getUnitNumber())
                .setBedrooms(unit.getBedrooms())
                .setTenantName(buildTenantName(tenant))
                .setEmail(tenant.getEmail())
                .setTelephone(tenant.getPhone())
                .setSecondaryTenantName(tenant.getSecondaryTenantName())
                .setSecondaryTenantPhone(tenant.getSecondaryTenantPhone());
    }

    private String buildTenantName(TenantDto tenant) {
        String firstName = tenant.getFirstName() == null ? "" : tenant.getFirstName().trim();
        String lastName = tenant.getLastName() == null ? "" : tenant.getLastName().trim();

        String fullName = (firstName + " " + lastName).trim();

        if (fullName.isBlank()) {
            return "Tenant #" + tenant.getId();
        }

        return fullName;
    }

    private int compareUnitNumbers(String first, String second) {
        int firstNumber = extractLeadingNumber(first);
        int secondNumber = extractLeadingNumber(second);

        if (firstNumber != secondNumber) {
            return Integer.compare(firstNumber, secondNumber);
        }

        return first.compareToIgnoreCase(second);
    }

    private int extractLeadingNumber(String unitNumber) {
        if (unitNumber == null || unitNumber.isBlank()) {
            return Integer.MAX_VALUE;
        }

        String number = unitNumber.replaceAll("[^0-9].*", "");

        if (number.isBlank()) {
            return Integer.MAX_VALUE;
        }

        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException ex) {
            return Integer.MAX_VALUE;
        }
    }
}