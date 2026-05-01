package com.cornerstone.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "units")
public class UnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String unitNumber;

    @Column(nullable = false)
    private String address;

    @Column(name = "status")
    private String status;

    @Column(name = "ownership_type")
    private String ownershipType;

    @Column(name = "program_type")
    private String programType;

    @Column(name = "managed_by_cornerstone")
    private Boolean managedByCornerstone;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // 🔥 NUEVOS CAMPOS (características de la unidad)

    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Column(name = "bathrooms")
    private Integer bathrooms;

    @Column(name = "floors")
    private Integer floors;

    @Column(name = "has_basement")
    private Boolean hasBasement;

    // Getters & Setters

    public Long getId() { return id; }

    public UnitEntity setId(Long id) { this.id = id; return this; }

    public String getUnitNumber() { return unitNumber; }

    public UnitEntity setUnitNumber(String unitNumber) { this.unitNumber = unitNumber; return this; }

    public String getAddress() { return address; }

    public UnitEntity setAddress(String address) { this.address = address; return this; }

    public String getStatus() { return status; }

    public UnitEntity setStatus(String status) { this.status = status; return this; }

    public String getOwnershipType() { return ownershipType; }

    public UnitEntity setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
        return this;
    }

    public String getProgramType() { return programType; }

    public UnitEntity setProgramType(String programType) {
        this.programType = programType;
        return this;
    }

    public Boolean getManagedByCornerstone() { return managedByCornerstone; }

    public UnitEntity setManagedByCornerstone(Boolean managedByCornerstone) {
        this.managedByCornerstone = managedByCornerstone;
        return this;
    }

    public String getNotes() { return notes; }

    public UnitEntity setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Integer getBedrooms() { return bedrooms; }

    public UnitEntity setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
        return this;
    }

    public Integer getBathrooms() { return bathrooms; }

    public UnitEntity setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
        return this;
    }

    public Integer getFloors() { return floors; }

    public UnitEntity setFloors(Integer floors) {
        this.floors = floors;
        return this;
    }

    public Boolean getHasBasement() { return hasBasement; }

    public UnitEntity setHasBasement(Boolean hasBasement) {
        this.hasBasement = hasBasement;
        return this;
    }
}