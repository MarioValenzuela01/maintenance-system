package com.cornerstone.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LeaseDto {
    private Long id;
    private Long tenantId;
    private Long unitId;
    private String tenantName;
    private String unitNumber;

    private LocalDate startDate;
    private LocalDate endDate;


    private Integer adultsCount;
    private Integer childrenCount;
    private Integer seniorsCount;
    private Boolean smokers;
    private Integer petsCount;
    private Integer carsCount;

    private String programNotes;

    private String unitDisplayName;

    public LeaseDto() {
    }

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

    public Integer getAdultsCount() { return adultsCount; }
    public LeaseDto setAdultsCount(Integer adultsCount) { this.adultsCount = adultsCount; return this; }

    public Integer getChildrenCount() { return childrenCount; }
    public LeaseDto setChildrenCount(Integer childrenCount) { this.childrenCount = childrenCount; return this; }

    public Integer getSeniorsCount() { return seniorsCount; }
    public LeaseDto setSeniorsCount(Integer seniorsCount) { this.seniorsCount = seniorsCount; return this; }

    public Boolean getSmokers() { return smokers; }
    public LeaseDto setSmokers(Boolean smokers) { this.smokers = smokers; return this; }

    public Integer getPetsCount() { return petsCount; }
    public LeaseDto setPetsCount(Integer petsCount) { this.petsCount = petsCount; return this; }

    public Integer getCarsCount() { return carsCount; }
    public LeaseDto setCarsCount(Integer carsCount) { this.carsCount = carsCount; return this; }

    public String getProgramNotes() { return programNotes; }
    public LeaseDto setProgramNotes(String programNotes) { this.programNotes = programNotes; return this; }

    public String getTenantName() {
        return tenantName;
    }

    public LeaseDto setTenantName(String tenantName) {
        this.tenantName = tenantName;
        return this;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public LeaseDto setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
        return this;
    }

    public String getUnitDisplayLabel() {
        if (unitDisplayName != null && !unitDisplayName.trim().isEmpty()) {
            return unitDisplayName;
        }

        return unitNumber;
    }

    public LeaseDto setUnitDisplayName(String unitDisplayName) {
        this.unitDisplayName = unitDisplayName;
        return this;
    }
}