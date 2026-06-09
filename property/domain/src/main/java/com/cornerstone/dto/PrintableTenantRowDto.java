package com.cornerstone.dto;

public class PrintableTenantRowDto {

    private String unitNumber;
    private Integer bedrooms;
    private String tenantName;
    private String email;
    private String telephone;
    private String secondaryTenantName;
    private String secondaryTenantPhone;

    public String getUnitNumber() {
        return unitNumber;
    }

    public PrintableTenantRowDto setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
        return this;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public PrintableTenantRowDto setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
        return this;
    }

    public String getTenantName() {
        return tenantName;
    }

    public PrintableTenantRowDto setTenantName(String tenantName) {
        this.tenantName = tenantName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PrintableTenantRowDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public PrintableTenantRowDto setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public String getSecondaryTenantName() {
        return secondaryTenantName;
    }

    public PrintableTenantRowDto setSecondaryTenantName(String secondaryTenantName) {
        this.secondaryTenantName = secondaryTenantName;
        return this;
    }

    public String getSecondaryTenantPhone() {
        return secondaryTenantPhone;
    }

    public PrintableTenantRowDto setSecondaryTenantPhone(String secondaryTenantPhone) {
        this.secondaryTenantPhone = secondaryTenantPhone;
        return this;
    }
}