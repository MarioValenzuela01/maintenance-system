package com.cornerstone.controller;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.service.LeaseService;
import com.cornerstone.service.TenantService;
import com.cornerstone.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/leases")
public class LeaseController {

    private final LeaseService leaseService;
    private final TenantService tenantService;
    private final UnitService unitService;

    // Inyectamos los 3 servicios
    public LeaseController(LeaseService leaseService, TenantService tenantService, UnitService unitService) {
        this.leaseService = leaseService;
        this.tenantService = tenantService;
        this.unitService = unitService;
    }

    // Mostrar todos los contratos
    @GetMapping
    public String list(Model model) {
        model.addAttribute("leases", leaseService.getAll());
        return "leases/list";
    }

    // Mostrar formulario para nuevo contrato
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("lease", new LeaseDto());
        // Pasamos la lista de arrendatarios y unidades a la vista para los <select>
        model.addAttribute("tenants", tenantService.getAll());
        model.addAttribute("units", unitService.getAll());
        return "leases/create";
    }

    // Guardar el contrato
    @PostMapping("/create")
    public String createSubmit(@ModelAttribute("lease") LeaseDto lease) {
        leaseService.create(lease);
        return "redirect:/leases";
    }

    // Finalizar un contrato (La regla de negocio de no borrar, solo marcar fecha)
    @PostMapping("/end/{id}")
    public String endLease(@PathVariable("id") Long id) {
        leaseService.endLease(id);
        return "redirect:/leases";
    }
}