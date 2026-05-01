package com.cornerstone.controller;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.service.LeaseService;
import com.cornerstone.service.TenantService;
import com.cornerstone.service.UnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String list(@RequestParam(name = "showHistory", defaultValue = "false") boolean showHistory,
                       Model model) {

        List<LeaseDto> leases = leaseService.getAll();

        if (!showHistory) {
            leases = leases.stream()
                    .filter(l -> l.getEndDate() == null)
                    .toList();
        }

        model.addAttribute("leases", leases);
        model.addAttribute("showHistory", showHistory);

        return "leases/list";
    }

    // Mostrar formulario para nuevo contrato
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("lease", new LeaseDto());
        // Pasamos la lista de arrendatarios y unidades a la vista para los <select>
        model.addAttribute("tenants", leaseService.getAvailableTenants());
        model.addAttribute("units", leaseService.getAvailableUnits());
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

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        var lease = leaseService.get(id);

        if (lease.isPresent()) {
            model.addAttribute("lease", lease.get());
            model.addAttribute("tenants", tenantService.getAll());
            model.addAttribute("units", unitService.getAll());
            return "leases/edit";
        }

        return "redirect:/leases";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable("id") Long id,
                             @ModelAttribute("lease") LeaseDto lease) {
        leaseService.update(id, lease);
        return "redirect:/leases";
    }
}