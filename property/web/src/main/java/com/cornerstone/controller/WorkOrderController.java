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

import com.cornerstone.dto.WorkOrderTimeLogDto;
import com.cornerstone.service.WorkOrderTimeLogService;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

import java.security.Principal;
import java.util.List;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/work-orders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;
    private final UnitService unitService;
    private final AppUserRepository appUserRepository;
    private final WorkOrderTimeLogService timeLogService;

    public WorkOrderController(WorkOrderService workOrderService,
                               UnitService unitService,
                               AppUserRepository appUserRepository,
                               WorkOrderTimeLogService timeLogService) {
        this.workOrderService = workOrderService;
        this.unitService = unitService;
        this.appUserRepository = appUserRepository;
        this.timeLogService = timeLogService;
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
        prepareTimeLogModel(model, selectedWorkOrder);

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
        prepareTimeLogModel(model, selectedWorkOrder);

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

    @PostMapping("/{id}/time-logs/create")
    public String createTimeLog(@PathVariable("id") Long id,
                                @RequestParam("workDate")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                LocalDate workDate,
                                @RequestParam("startTime")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                LocalTime startTime,
                                @RequestParam("endTime")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
                                LocalTime endTime,
                                @RequestParam(name = "notes", required = false) String notes,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {

        WorkOrderDto workOrder = workOrderService.get(id)
                .orElseThrow(() -> new RuntimeException("Work order not found"));

        if (!isAccessibleUnit(workOrder)) {
            return "redirect:/work-orders?selectedId=" + id;
        }

        if (!canManageWorkOrders(authentication)
                && !authentication.getName().equals(workOrder.getAssignedToUsername())) {
            return "redirect:/work-orders/my";
        }

        AppUserEntity currentUser = appUserRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        WorkOrderTimeLogDto dto = new WorkOrderTimeLogDto()
                .setWorkOrderId(workOrder.getId())
                .setUnitId(workOrder.getUnitId())
                .setUserId(currentUser.getId())
                .setWorkDate(workDate)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setNotes(notes);

        try {
            timeLogService.create(dto);
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("timeLogError", ex.getMessage());

            if (canManageWorkOrders(authentication)) {
                return "redirect:/work-orders?selectedId=" + id;
            }

            return "redirect:/work-orders/my?selectedId=" + id;
        }

        if (canManageWorkOrders(authentication)) {
            return "redirect:/work-orders?selectedId=" + id;
        }

        return "redirect:/work-orders/my?selectedId=" + id;
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

    private void prepareTimeLogModel(Model model, WorkOrderDto selectedWorkOrder) {

        boolean accessibleUnit = isAccessibleUnit(selectedWorkOrder);

        model.addAttribute("isAccessibleUnit", accessibleUnit);
        model.addAttribute("today", LocalDate.now());

        if (!accessibleUnit || selectedWorkOrder == null) {
            model.addAttribute("timeLogs", List.of());
            model.addAttribute("totalMinutesWorked", 0);
            model.addAttribute("totalTimeWorked", "0h 0m");
            return;
        }

        List<WorkOrderTimeLogDto> timeLogs = timeLogService.getByWorkOrderId(selectedWorkOrder.getId());
        int totalMinutes = timeLogService.getTotalMinutesByWorkOrderId(selectedWorkOrder.getId());

        model.addAttribute("timeLogs", timeLogs);
        model.addAttribute("totalMinutesWorked", totalMinutes);
        model.addAttribute("totalTimeWorked", timeLogService.formatMinutes(totalMinutes));
    }

    private boolean isAccessibleUnit(WorkOrderDto workOrder) {
        if (workOrder == null || workOrder.getUnitNumber() == null) {
            return false;
        }

        return "146-1".equalsIgnoreCase(workOrder.getUnitNumber())
                || "146-2".equalsIgnoreCase(workOrder.getUnitNumber())
                || "146-3".equalsIgnoreCase(workOrder.getUnitNumber())
                || "146-4".equalsIgnoreCase(workOrder.getUnitNumber());
    }
}