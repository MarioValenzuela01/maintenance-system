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
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer floors;
    private Boolean hasBasement;
    private String displayName;

    // AGREGADO: campo archived para reflejar el soft delete en el DTO
    private Boolean archived = false;

    private String googleMapsUrl;

    public UnitDto() {}

    public Long getId() { return id; }
    public UnitDto setId(Long id) { this.id = id; return this; }

    public String getUnitNumber() { return unitNumber; }
    public UnitDto setUnitNumber(String unitNumber) { this.unitNumber = unitNumber; return this; }

    public String getAddress() { return address; }
    public UnitDto setAddress(String address) { this.address = address; return this; }

    public String getStatus() { return status; }
    public UnitDto setStatus(String status) { this.status = status; return this; }

    public String getOwnershipType() { return ownershipType; }
    public UnitDto setOwnershipType(String ownershipType) { this.ownershipType = ownershipType; return this; }

    public String getProgramType() { return programType; }
    public UnitDto setProgramType(String programType) { this.programType = programType; return this; }

    public Boolean getManagedByCornerstone() { return managedByCornerstone; }
    public UnitDto setManagedByCornerstone(Boolean managedByCornerstone) { this.managedByCornerstone = managedByCornerstone; return this; }

    public String getNotes() { return notes; }
    public UnitDto setNotes(String notes) { this.notes = notes; return this; }

    public Integer getBedrooms() { return bedrooms; }
    public UnitDto setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; return this; }

    public Integer getBathrooms() { return bathrooms; }
    public UnitDto setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; return this; }

    public Integer getFloors() { return floors; }
    public UnitDto setFloors(Integer floors) { this.floors = floors; return this; }

    public Boolean getHasBasement() { return hasBasement; }
    public UnitDto setHasBasement(Boolean hasBasement) { this.hasBasement = hasBasement; return this; }

    // AGREGADO: getter y setter para archived
    public Boolean getArchived() { return archived; }
    public UnitDto setArchived(Boolean archived) { this.archived = archived; return this; }

    public String getGoogleMapsUrl() {return googleMapsUrl;}
    public UnitDto setGoogleMapsUrl(String googleMapsUrl) {this.googleMapsUrl = googleMapsUrl; return this;
    }


    public String getDisplayName() {
        if (displayName != null && !displayName.isBlank()) {
            return displayName;
        }

        StringBuilder name = new StringBuilder();

        if (unitNumber != null && !unitNumber.isBlank()) {
            name.append(unitNumber);
        } else if (address != null && !address.isBlank()) {
            name.append(address);
        } else if (id != null) {
            name.append("Unit ").append(id);
        } else {
            name.append("Unit");
        }

        if (programType != null && !programType.isBlank()) {
            name.append(" - ").append(programType);
        }

        if (ownershipType != null && !ownershipType.isBlank()) {
            name.append(" - ").append(ownershipType);
        }

        return name.toString();
    }

    public UnitDto setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}