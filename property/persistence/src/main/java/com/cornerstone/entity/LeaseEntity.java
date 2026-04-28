package com.cornerstone.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

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
}