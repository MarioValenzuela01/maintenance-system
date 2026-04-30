package com.cornerstone.dto;

public class TenantDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    private String emergencyContactName;
    private String emergencyContactPhone;

    private String notes;

    public Long getId() { return id; }
    public TenantDto setId(Long id) { this.id = id; return this; }

    public String getFirstName() { return firstName; }
    public TenantDto setFirstName(String firstName) { this.firstName = firstName; return this; }

    public String getLastName() { return lastName; }
    public TenantDto setLastName(String lastName) { this.lastName = lastName; return this; }

    public String getPhone() { return phone; }
    public TenantDto setPhone(String phone) { this.phone = phone; return this; }

    public String getEmail() { return email; }
    public TenantDto setEmail(String email) { this.email = email; return this; }

    public String getEmergencyContactName() { return emergencyContactName; }
    public TenantDto setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; return this; }

    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public TenantDto setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; return this; }

    public String getNotes() { return notes; }
    public TenantDto setNotes(String notes) { this.notes = notes; return this; }
}