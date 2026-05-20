package com.cornerstone.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "unit_maintenance_history")
public class UnitMaintenanceHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="unit_id", nullable = false)
    private Long unitId;

    @Column(nullable = false)
    private String category;

    @Column(name="item_name", nullable = false)
    private String itemName;

    @Column(name="completed_date")
    private LocalDate completedDate;

    @Column(length = 1000)
    private String notes;

    public Long getId() { return id; }
    public UnitMaintenanceHistoryEntity setId(Long id) { this.id = id; return this; }

    public Long getUnitId() { return unitId; }
    public UnitMaintenanceHistoryEntity setUnitId(Long unitId) { this.unitId = unitId; return this; }

    public String getCategory() { return category; }
    public UnitMaintenanceHistoryEntity setCategory(String category) { this.category = category; return this; }

    public String getItemName() { return itemName; }
    public UnitMaintenanceHistoryEntity setItemName(String itemName) { this.itemName = itemName; return this; }

    public LocalDate getCompletedDate() { return completedDate; }
    public UnitMaintenanceHistoryEntity setCompletedDate(LocalDate completedDate) { this.completedDate = completedDate; return this; }

    public String getNotes() { return notes; }
    public UnitMaintenanceHistoryEntity setNotes(String notes) { this.notes = notes; return this; }
}