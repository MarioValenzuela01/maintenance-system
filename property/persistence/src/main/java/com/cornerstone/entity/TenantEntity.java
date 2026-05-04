package com.cornerstone.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tenants")
public class TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean active = true;

    // AGREGADO: campo para el nombre del contacto de emergencia
    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    // AGREGADO: campo para el teléfono del contacto de emergencia
    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;

    // AGREGADO: campo para notas adicionales del tenant
    @Column(columnDefinition = "TEXT")
    private String notes;

    public TenantEntity() {}

    public Long getId() { return id; }
    public TenantEntity setId(Long id) { this.id = id; return this; }

    public String getFirstName() { return firstName; }
    public TenantEntity setFirstName(String firstName) { this.firstName = firstName; return this; }

    public String getLastName() { return lastName; }
    public TenantEntity setLastName(String lastName) { this.lastName = lastName; return this; }

    public String getEmail() { return email; }
    public TenantEntity setEmail(String email) { this.email = email; return this; }

    public String getPhoneNumber() { return phoneNumber; }
    public TenantEntity setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }

    public Boolean getActive() { return active; }
    public TenantEntity setActive(Boolean active) { this.active = active; return this; }

    // AGREGADO: getter y setter para emergencyContactName
    public String getEmergencyContactName() { return emergencyContactName; }
    public TenantEntity setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
        return this;
    }

    // AGREGADO: getter y setter para emergencyContactPhone
    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public TenantEntity setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
        return this;
    }

    // AGREGADO: getter y setter para notes
    public String getNotes() { return notes; }
    public TenantEntity setNotes(String notes) { this.notes = notes; return this; }
}