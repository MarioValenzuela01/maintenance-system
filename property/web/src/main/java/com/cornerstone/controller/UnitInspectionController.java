package com.cornerstone.controller;

import com.cornerstone.dto.UnitInspectionDto;
import com.cornerstone.dto.UnitInspectionItemDto;
import com.cornerstone.service.UnitInspectionService;
import com.cornerstone.service.UnitService;
import com.cornerstone.service.TenantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/unit-inspections")
public class UnitInspectionController {

    private final UnitInspectionService inspectionService;
    private final UnitService unitService;
    private final TenantService tenantService;

    public UnitInspectionController(UnitInspectionService inspectionService,
                                    UnitService unitService,
                                    TenantService tenantService) {
        this.inspectionService = inspectionService;
        this.unitService = unitService;
        this.tenantService = tenantService;
    }

    @GetMapping
    public String list(@RequestParam(name = "unitId", required = false) Long unitId,
                       @RequestParam(name = "inspectionType", required = false) String inspectionType,
                       Model model) {

        model.addAttribute("inspections", inspectionService.search(unitId, inspectionType));
        model.addAttribute("units", unitService.getAll());
        model.addAttribute("selectedUnitId", unitId);
        model.addAttribute("selectedInspectionType", inspectionType);

        return "unit-inspection/list";
    }

    @GetMapping("/create")
    public String createForm(@RequestParam(name = "unitId", required = false) Long unitId,
                             Model model) {

        UnitInspectionDto inspection = new UnitInspectionDto();
        inspection.setInspectionDate(LocalDate.now());
        inspection.setInspectionType("Move In");
        inspection.setUnitId(unitId);
        inspection.setItems(defaultInspectionItems());

        addFormData(model, inspection);

        return "unit-inspection/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("inspection") UnitInspectionDto inspection) {
        UnitInspectionDto saved = inspectionService.create(inspection);
        return "redirect:/unit-inspections/" + saved.getId();
    }

    @GetMapping("/{id}")
    public String details(@PathVariable(name = "id") Long id, Model model) {
        UnitInspectionDto inspection = inspectionService.get(id)
                .orElseThrow(() -> new IllegalArgumentException("Inspection not found: " + id));

        model.addAttribute("inspection", inspection);
        return "unit-inspection/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable(name = "id") Long id, Model model) {
        UnitInspectionDto inspection = inspectionService.get(id)
                .orElseThrow(() -> new IllegalArgumentException("Inspection not found: " + id));

        addFormData(model, inspection);

        return "unit-inspection/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Long id,
                       @ModelAttribute("inspection") UnitInspectionDto inspection) {

        UnitInspectionDto saved = inspectionService.update(id, inspection);
        return "redirect:/unit-inspections/" + saved.getId();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id) {
        inspectionService.delete(id);
        return "redirect:/unit-inspections";
    }

    private void addFormData(Model model, UnitInspectionDto inspection) {
        model.addAttribute("inspection", inspection);
        model.addAttribute("units", unitService.getAll());
        model.addAttribute("tenants", tenantService.getAll());

        model.addAttribute("inspectionTypes", List.of(
                "Annual",
                "Follow Up",
                "Pre-Move",
                "Move Out",
                "Move In",
                "Emergency"
        ));
    }

    private List<UnitInspectionItemDto> defaultInspectionItems() {
        List<UnitInspectionItemDto> items = new ArrayList<>();

        add(items, "Front Entry", "door & hardware");
        add(items, "Front Entry", "storm door & hardware");
        add(items, "Front Entry", "weather stripping");
        add(items, "Front Entry", "ceiling");
        add(items, "Front Entry", "walls");
        add(items, "Front Entry", "windows");
        add(items, "Front Entry", "flooring");
        add(items, "Front Entry", "baseboards & heaters");
        add(items, "Front Entry", "lights & doorbell");
        add(items, "Front Entry", "cleanliness / tripping hazard");

        add(items, "Living Room", "door & hardware");
        add(items, "Living Room", "walls");
        add(items, "Living Room", "ceiling");
        add(items, "Living Room", "flooring");
        add(items, "Living Room", "windows / screens");
        add(items, "Living Room", "light fixtures");
        add(items, "Living Room", "electrical");
        add(items, "Living Room", "vents");

        add(items, "Kitchen", "walls");
        add(items, "Kitchen", "ceiling");
        add(items, "Kitchen", "flooring");
        add(items, "Kitchen", "range hood / fan");
        add(items, "Kitchen", "cabinets");
        add(items, "Kitchen", "cabinet hinges");
        add(items, "Kitchen", "countertop");
        add(items, "Kitchen", "faucet & plumbing");
        add(items, "Kitchen", "stove");
        add(items, "Kitchen", "refrigerator");
        add(items, "Kitchen", "vents");
        add(items, "Kitchen", "fire extinguisher");
        add(items, "Kitchen", "cleanliness / tripping hazard");

        add(items, "Bathroom - Main", "door & hardware");
        add(items, "Bathroom - Main", "walls & ceiling");
        add(items, "Bathroom - Main", "flooring");
        add(items, "Bathroom - Main", "medicine cabinet");
        add(items, "Bathroom - Main", "mirrors");
        add(items, "Bathroom - Main", "vanity");
        add(items, "Bathroom - Main", "basin & fixture");
        add(items, "Bathroom - Main", "toilet & plumbing");
        add(items, "Bathroom - Main", "bathtub & fixtures");
        add(items, "Bathroom - Main", "fan & vents");
        add(items, "Bathroom - Main", "cleanliness / tripping hazard");

        add(items, "Master Bedroom", "door & hardware");
        add(items, "Master Bedroom", "walls & ceiling");
        add(items, "Master Bedroom", "closet & closet doors");
        add(items, "Master Bedroom", "flooring");
        add(items, "Master Bedroom", "baseboard & heaters");
        add(items, "Master Bedroom", "windows & screens");
        add(items, "Master Bedroom", "electrical");
        add(items, "Master Bedroom", "vents");

        add(items, "Basement", "door & hardware");
        add(items, "Basement", "handrails");
        add(items, "Basement", "walls");
        add(items, "Basement", "flooring");
        add(items, "Basement", "electrical panel");
        add(items, "Basement", "hot water tank date");
        add(items, "Basement", "plumbing");

        add(items, "Exterior", "driveway / asphalt");
        add(items, "Exterior", "sidewalks");
        add(items, "Exterior", "front steps");
        add(items, "Exterior", "back steps");
        add(items, "Exterior", "roof");
        add(items, "Exterior", "fence");
        add(items, "Exterior", "gate");
        add(items, "Exterior", "outside lights");
        add(items, "Exterior", "outside plugs");
        add(items, "Exterior", "down spout / gutters");

        return items;
    }

    private void add(List<UnitInspectionItemDto> items, String area, String itemName) {
        items.add(new UnitInspectionItemDto()
                .setArea(area)
                .setItemName(itemName)
                .setStatus("OK"));
    }
}