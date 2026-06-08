package com.cornerstone.controller;

import com.cornerstone.dto.WorkOrderDto;
import com.cornerstone.entity.AppUserEntity;
import com.cornerstone.repository.AppUserRepository;
import com.cornerstone.service.UnitService;
import com.cornerstone.service.WorkOrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/work-orders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;
    private final UnitService unitService;
    private final AppUserRepository appUserRepository;

    public WorkOrderController(WorkOrderService workOrderService,
                               UnitService unitService,
                               AppUserRepository appUserRepository) {
        this.workOrderService = workOrderService;
        this.unitService = unitService;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping
    public String list(@RequestParam(name = "selectedId", required = false) Long selectedId,
                       Authentication authentication,
                       Model model) {

        List<WorkOrderDto> workOrders;

        if (canManageWorkOrders(authentication)) {
            workOrders = workOrderService.getAll();
        } else {
            workOrders = workOrderService.getMyOrders(authentication.getName());
        }

        WorkOrderDto selectedWorkOrder = getSelectedWorkOrder(workOrders, selectedId);

        model.addAttribute("workOrders", workOrders);
        model.addAttribute("selectedWorkOrder", selectedWorkOrder);
        model.addAttribute("pageTitle", canManageWorkOrders(authentication) ? "Work Orders" : "My Work Orders");
        model.addAttribute("pageSubtitle", "Select a work order on the left to view details.");
        model.addAttribute("workOrdersBasePath", "/work-orders");

        return "work-orders/list";
    }

    @GetMapping("/my")
    public String myOrders(@RequestParam(name = "selectedId", required = false) Long selectedId,
                           Principal principal,
                           Model model) {

        List<WorkOrderDto> workOrders = workOrderService.getMyOrders(principal.getName());

        WorkOrderDto selectedWorkOrder = getSelectedWorkOrder(workOrders, selectedId);

        model.addAttribute("workOrders", workOrders);
        model.addAttribute("selectedWorkOrder", selectedWorkOrder);
        model.addAttribute("pageTitle", "My Work Orders");
        model.addAttribute("pageSubtitle", "Select one of your assigned work orders to view details.");
        model.addAttribute("workOrdersBasePath", "/work-orders/my");

        return "work-orders/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        WorkOrderDto workOrder = new WorkOrderDto();
        workOrder.setStatus("ASSIGNED");
        workOrder.setPriority("MEDIUM");

        model.addAttribute("workOrder", workOrder);
        prepareFormData(model);

        return "work-orders/create";
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute("workOrder") WorkOrderDto workOrder,
                               Principal principal) {

        AppUserEntity currentUser = appUserRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        workOrder.setCreatedByUserId(currentUser.getId());
        workOrder.setStatus("ASSIGNED");

        workOrderService.create(workOrder);

        return "redirect:/work-orders";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        WorkOrderDto workOrder = workOrderService.get(id)
                .orElseThrow(() -> new RuntimeException("Work order not found"));

        model.addAttribute("workOrder", workOrder);
        prepareFormData(model);

        return "work-orders/edit";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable("id") Long id,
                             @ModelAttribute("workOrder") WorkOrderDto workOrder) {

        workOrderService.update(id, workOrder);

        return "redirect:/work-orders/details/" + id;
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id,
                          Authentication authentication,
                          Model model) {

        WorkOrderDto workOrder = workOrderService.get(id)
                .orElseThrow(() -> new RuntimeException("Work order not found"));

        if (!canManageWorkOrders(authentication)
                && !authentication.getName().equals(workOrder.getAssignedToUsername())) {
            return "redirect:/work-orders/my";
        }

        model.addAttribute("workOrder", workOrder);

        return "work-orders/details";
    }

    @PostMapping("/status/{id}")
    public String changeStatus(@PathVariable("id") Long id,
                               @RequestParam("status") String status,
                               Authentication authentication) {

        WorkOrderDto workOrder = workOrderService.get(id)
                .orElseThrow(() -> new RuntimeException("Work order not found"));

        if (!canManageWorkOrders(authentication)
                && !authentication.getName().equals(workOrder.getAssignedToUsername())) {
            return "redirect:/work-orders/my";
        }

        if (!canManageWorkOrders(authentication)
                && !"IN_PROGRESS".equalsIgnoreCase(status)
                && !"COMPLETED".equalsIgnoreCase(status)) {
            return "redirect:/work-orders/my";
        }

        workOrderService.changeStatus(id, status);

        if (canManageWorkOrders(authentication)) {
            return "redirect:/work-orders/details/" + id;
        }

        return "redirect:/work-orders/my";
    }

    @PostMapping("/status-with-notes/{id}")
    public String changeStatusWithNotes(@PathVariable("id") Long id,
                                        @RequestParam("status") String status,
                                        @RequestParam(name = "notes", required = false) String notes,
                                        Authentication authentication) {

        WorkOrderDto workOrder = workOrderService.get(id)
                .orElseThrow(() -> new RuntimeException("Work order not found"));

        if (!canManageWorkOrders(authentication)
                && !authentication.getName().equals(workOrder.getAssignedToUsername())) {
            return "redirect:/work-orders/my";
        }

        if (!canManageWorkOrders(authentication)
                && !"COMPLETED".equalsIgnoreCase(status)
                && !"IN_PROGRESS".equalsIgnoreCase(status)) {
            return "redirect:/work-orders/my";
        }

        workOrderService.changeStatusWithNotes(id, status, notes);

        if (canManageWorkOrders(authentication)) {
            return "redirect:/work-orders?selectedId=" + id;
        }

        return "redirect:/work-orders/my?selectedId=" + id;
    }

    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable("id") Long id) {
        workOrderService.cancel(id);
        return "redirect:/work-orders";
    }

    private void prepareFormData(Model model) {
        model.addAttribute("units", unitService.getAll());
        model.addAttribute("users", getAssignableUsers());

        model.addAttribute("workTypes", List.of(
                "PLUMBING",
                "ELECTRICAL",
                "CLEANING",
                "INSPECTION",
                "GENERAL_REPAIR",
                "OTHER"
        ));

        model.addAttribute("priorities", List.of(
                "LOW",
                "MEDIUM",
                "HIGH",
                "URGENT"
        ));

        model.addAttribute("statuses", List.of(
                "ASSIGNED",
                "IN_PROGRESS",
                "COMPLETED",
                "CANCELLED"
        ));
    }

    private List<AppUserEntity> getAssignableUsers() {
        return appUserRepository.findAll()
                .stream()
                .filter(user -> Boolean.TRUE.equals(user.getEnabled()))
                .filter(user -> "USER".equalsIgnoreCase(user.getRole()))
                .toList();
    }

    private boolean canManageWorkOrders(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        "ROLE_SUPER_ADMIN".equals(authority.getAuthority())
                                || "ROLE_ADMIN".equals(authority.getAuthority()));
    }

    private WorkOrderDto getSelectedWorkOrder(List<WorkOrderDto> workOrders, Long selectedId) {

        WorkOrderDto selectedWorkOrder = null;

        if (selectedId != null) {
            selectedWorkOrder = workOrders.stream()
                    .filter(workOrder -> workOrder.getId().equals(selectedId))
                    .findFirst()
                    .orElse(null);
        }

        if (selectedWorkOrder == null && !workOrders.isEmpty()) {
            selectedWorkOrder = workOrders.get(0);
        }

        return selectedWorkOrder;
    }
}