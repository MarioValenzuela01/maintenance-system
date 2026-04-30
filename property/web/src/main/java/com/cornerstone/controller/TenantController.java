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
}