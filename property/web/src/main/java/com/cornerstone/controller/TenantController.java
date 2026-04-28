package com.cornerstone.controller;

import com.cornerstone.dto.TenantDto;
import com.cornerstone.service.TenantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    // --- LEER (Read) ---
    @GetMapping
    public String list(Model model) {
        model.addAttribute("tenants", tenantService.getAll());
        return "tenants/list";
    }

    // --- CREAR (Create) ---
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("tenant", new TenantDto());
        return "tenants/create";
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute("tenant") TenantDto tenant) {
        tenantService.create(tenant);
        return "redirect:/tenants";
    }

    // --- ACTUALIZAR (Update) ---
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Optional<TenantDto> tenant = tenantService.get(id);
        if (tenant.isPresent()) {
            model.addAttribute("tenant", tenant.get());
            return "tenants/edit";
        }
        return "redirect:/tenants";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable("id") Long id, @ModelAttribute("tenant") TenantDto tenant) {
        tenantService.update(id, tenant);
        return "redirect:/tenants";
    }

    // --- ELIMINAR (Delete) ---
    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable("id") Long id, Model model) {
        Optional<TenantDto> tenant = tenantService.get(id);
        if (tenant.isPresent()) {
            model.addAttribute("tenant", tenant.get());
            return "tenants/delete";
        }
        return "redirect:/tenants";
    }

    @PostMapping("/delete/{id}")
    public String deleteSubmit(@PathVariable("id") Long id) {
        tenantService.delete(id);
        return "redirect:/tenants";
    }
}