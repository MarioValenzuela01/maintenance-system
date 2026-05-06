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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tenants", tenantService.getAll());
        return "tenants/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("tenant", new TenantDto());
        return "tenants/create";
    }

    @PostMapping("/create")
    public String create(TenantDto tenant) {
        tenantService.create(tenant);
        return "redirect:/tenants";
    }
    @GetMapping("/edit/{id}")
    public String editTenant(@PathVariable("id") Long id, Model model) {
        Optional<TenantDto> tenant = tenantService.get(id);

        if (tenant.isEmpty()) {
            return "redirect:/tenants";
        }

        model.addAttribute("tenant", tenant.get());
        return "tenants/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateTenant(@PathVariable("id") Long id, @ModelAttribute("tenant") TenantDto tenant) {
        tenantService.update(id, tenant);
        return "redirect:/tenants";
    }

    @GetMapping("/delete/{id}")
    public String deleteTenant(@PathVariable("id") Long id) {
        try {
            tenantService.delete(id);
        } catch (Exception ex) {
            return "redirect:/tenants?deleteError=true";
        }

        return "redirect:/tenants";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        var tenant = tenantService.get(id);

        if (tenant.isEmpty()) {
            return "redirect:/tenants";
        }

        model.addAttribute("tenant", tenant.get());
        return "tenants/details";
    }

}