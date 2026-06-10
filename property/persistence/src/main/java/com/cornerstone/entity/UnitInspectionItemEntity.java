package com.cornerstone.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "unit_inspection_items")
public class UnitInspectionItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String area;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "in_condition", columnDefinition = "TEXT")
    private String inCondition;

    @Column(name = "out_condition", columnDefinition = "TEXT")
    private String outCondition;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_id", nullable = false)
    private UnitInspectionEntity inspection;

    public Long getId() {
        return id;
    }

    public UnitInspectionItemEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getArea() {
        return area;
    }

    public UnitInspectionItemEntity setArea(String area) {
        this.area = area;
        return this;
    }

    public String getItemName() {
        return itemName;
    }

    public UnitInspectionItemEntity setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public String getInCondition() {
        return inCondition;
    }

    public UnitInspectionItemEntity setInCondition(String inCondition) {
        this.inCondition = inCondition;
        return this;
    }

    public String getOutCondition() {
        return outCondition;
    }

    public UnitInspectionItemEntity setOutCondition(String outCondition) {
        this.outCondition = outCondition;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public UnitInspectionItemEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public UnitInspectionItemEntity setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public UnitInspectionEntity getInspection() {
        return inspection;
    }

    public UnitInspectionItemEntity setInspection(UnitInspectionEntity inspection) {
        this.inspection = inspection;
        return this;
    }
}