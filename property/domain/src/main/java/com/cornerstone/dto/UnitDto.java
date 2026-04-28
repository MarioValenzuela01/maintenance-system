package com.cornerstone.dto;

public class UnitDto {
    private Long id;
    private String unitNumber;
    private String address;
    private String Status;



    // Constructores, Getters y Setters usando el patrón Builder
    public UnitDto() {}

    public Long getId() { return id; }
    public UnitDto setId(Long id) { this.id = id; return this; }

    public String getUnitNumber() { return unitNumber; }
    public UnitDto setUnitNumber(String unitNumber) { this.unitNumber = unitNumber; return this; }

    public String getAddress() { return address; }
    public UnitDto setAddress(String address) { this.address = address; return this; }

    public String getStatus() { return Status; }
    public UnitDto setStatus(String status) { this.Status = status; return this; }
}