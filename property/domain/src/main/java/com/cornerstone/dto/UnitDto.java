package com.cornerstone.dto;

public class UnitDto {
    private Long id;
    private String unitNumber;
    private String address;
    private String status;

    private String ownershipType;
    private String programType;
    private Boolean managedByCornerstone;
    private String notes;

    public UnitDto() {
    }

    public Long getId() {
        return id;
    }

    public UnitDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public UnitDto setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UnitDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public UnitDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public UnitDto setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
        return this;
    }

    public String getProgramType() {
        return programType;
    }

    public UnitDto setProgramType(String programType) {
        this.programType = programType;
        return this;
    }

    public Boolean getManagedByCornerstone() {
        return managedByCornerstone;
    }

    public UnitDto setManagedByCornerstone(Boolean managedByCornerstone) {
        this.managedByCornerstone = managedByCornerstone;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public UnitDto setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}