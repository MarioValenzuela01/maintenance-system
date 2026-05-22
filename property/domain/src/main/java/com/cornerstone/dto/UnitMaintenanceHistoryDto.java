package com.cornerstone.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UnitMaintenanceHistoryDto {

    private Long id;
    private Long unitId;
    private String category;
    private String itemName;
    private Long tenantIdAtTime;
    private String tenantNameAtTime;
    private String unitNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completedDate;

    private String notes;

    public Long getId() { return id; }
    public UnitMaintenanceHistoryDto setId(Long id) { this.id = id; return this; }

    public Long getUnitId() { return unitId; }
    public UnitMaintenanceHistoryDto setUnitId(Long unitId) { this.unitId = unitId; return this; }

    public String getCategory() { return category; }
    public UnitMaintenanceHistoryDto setCategory(String category) { this.category = category; return this; }

    public String getItemName() { return itemName; }
    public UnitMaintenanceHistoryDto setItemName(String itemName) { this.itemName = itemName; return this; }

    public LocalDate getCompletedDate() { return completedDate; }
    public UnitMaintenanceHistoryDto setCompletedDate(LocalDate completedDate) { this.completedDate = completedDate; return this; }

    public String getNotes() { return notes; }
    public UnitMaintenanceHistoryDto setNotes(String notes) { this.notes = notes; return this; }

    public Long getTenantIdAtTime() { return tenantIdAtTime; }
    public UnitMaintenanceHistoryDto setTenantIdAtTime(Long tenantIdAtTime) {
        this.tenantIdAtTime = tenantIdAtTime;
        return this;
    }

    public String getTenantNameAtTime() { return tenantNameAtTime; }
    public UnitMaintenanceHistoryDto setTenantNameAtTime(String tenantNameAtTime) {
        this.tenantNameAtTime = tenantNameAtTime;
        return this;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public UnitMaintenanceHistoryDto setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
        return this;
    }
}