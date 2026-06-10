package com.cornerstone.dto;

public class UnitInspectionItemDto {

    private Long id;
    private String area;
    private String itemName;
    private String inCondition;
    private String outCondition;
    private String status;
    private String notes;

    public Long getId() {
        return id;
    }

    public UnitInspectionItemDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getArea() {
        return area;
    }

    public UnitInspectionItemDto setArea(String area) {
        this.area = area;
        return this;
    }

    public String getItemName() {
        return itemName;
    }

    public UnitInspectionItemDto setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public String getInCondition() {
        return inCondition;
    }

    public UnitInspectionItemDto setInCondition(String inCondition) {
        this.inCondition = inCondition;
        return this;
    }

    public String getOutCondition() {
        return outCondition;
    }

    public UnitInspectionItemDto setOutCondition(String outCondition) {
        this.outCondition = outCondition;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public UnitInspectionItemDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public UnitInspectionItemDto setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}