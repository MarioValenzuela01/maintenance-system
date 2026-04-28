package com.cornerstone.dto;

import java.time.LocalDate;

public class LeaseDto {
    private Long id;
    private Long tenantId;
    private Long unitId;
    private LocalDate startDate;
    private LocalDate endDate; // Será null mientras vivan ahí

    public LeaseDto() {}

    public Long getId() { return id; }
    public LeaseDto setId(Long id) { this.id = id; return this; }

    public Long getTenantId() { return tenantId; }
    public LeaseDto setTenantId(Long tenantId) { this.tenantId = tenantId; return this; }

    public Long getUnitId() { return unitId; }
    public LeaseDto setUnitId(Long unitId) { this.unitId = unitId; return this; }

    public LocalDate getStartDate() { return startDate; }
    public LeaseDto setStartDate(LocalDate startDate) { this.startDate = startDate; return this; }

    public LocalDate getEndDate() { return endDate; }
    public LeaseDto setEndDate(LocalDate endDate) { this.endDate = endDate; return this; }
}