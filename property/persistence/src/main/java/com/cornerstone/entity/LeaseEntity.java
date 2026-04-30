package com.cornerstone.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "leases")
public class LeaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private TenantEntity tenant;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column(name = "rent_amount")
    private BigDecimal rentAmount;

    @Column(name = "subsidy_amount")
    private BigDecimal subsidyAmount;

    @Column(name = "tenant_contribution")
    private BigDecimal tenantContribution;

    @Column(name = "adults_count")
    private Integer adultsCount;

    @Column(name = "children_count")
    private Integer childrenCount;

    @Column(name = "seniors_count")
    private Integer seniorsCount;

    @Column(name = "smokers")
    private Boolean smokers;

    @Column(name = "pets_count")
    private Integer petsCount;

    @Column(name = "cars_count")
    private Integer carsCount;

    @Column(name = "program_notes", columnDefinition = "TEXT")
    private String programNotes;

    public Long getId() { return id; }
    public LeaseEntity setId(Long id) { this.id = id; return this; }

    public TenantEntity getTenant() { return tenant; }
    public LeaseEntity setTenant(TenantEntity tenant) { this.tenant = tenant; return this; }

    public UnitEntity getUnit() { return unit; }
    public LeaseEntity setUnit(UnitEntity unit) { this.unit = unit; return this; }

    public LocalDate getStartDate() { return startDate; }
    public LeaseEntity setStartDate(LocalDate startDate) { this.startDate = startDate; return this; }

    public LocalDate getEndDate() { return endDate; }
    public LeaseEntity setEndDate(LocalDate endDate) { this.endDate = endDate; return this; }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public LeaseEntity setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
        return this;
    }

    public BigDecimal getSubsidyAmount() {
        return subsidyAmount;
    }

    public LeaseEntity setSubsidyAmount(BigDecimal subsidyAmount) {
        this.subsidyAmount = subsidyAmount;
        return this;
    }

    public BigDecimal getTenantContribution() {
        return tenantContribution;
    }

    public LeaseEntity setTenantContribution(BigDecimal tenantContribution) {
        this.tenantContribution = tenantContribution;
        return this;
    }

    public Integer getAdultsCount() {
        return adultsCount;
    }

    public LeaseEntity setAdultsCount(Integer adultsCount) {
        this.adultsCount = adultsCount;
        return this;
    }

    public Integer getChildrenCount() {
        return childrenCount;
    }

    public LeaseEntity setChildrenCount(Integer childrenCount) {
        this.childrenCount = childrenCount;
        return this;
    }

    public Integer getSeniorsCount() {
        return seniorsCount;
    }

    public LeaseEntity setSeniorsCount(Integer seniorsCount) {
        this.seniorsCount = seniorsCount;
        return this;
    }

    public Boolean getSmokers() {
        return smokers;
    }

    public LeaseEntity setSmokers(Boolean smokers) {
        this.smokers = smokers;
        return this;
    }

    public Integer getPetsCount() {
        return petsCount;
    }

    public LeaseEntity setPetsCount(Integer petsCount) {
        this.petsCount = petsCount;
        return this;
    }

    public Integer getCarsCount() {
        return carsCount;
    }

    public LeaseEntity setCarsCount(Integer carsCount) {
        this.carsCount = carsCount;
        return this;
    }

    public String getProgramNotes() {
        return programNotes;
    }

    public LeaseEntity setProgramNotes(String programNotes) {
        this.programNotes = programNotes;
        return this;
    }
}