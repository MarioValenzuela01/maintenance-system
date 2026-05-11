package com.cornerstone.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tenants")
public class TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // BASIC
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "secondary_tenant_name")
    private String secondaryTenantName;

    @Column(name = "secondary_tenant_phone")
    private String secondaryTenantPhone;

    @Column(name = "secondary_tenant_email")
    private String secondaryTenantEmail;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // EMERGENCY
    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;

    // NOTES
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // HOUSEHOLD
    @Column(name = "children_count")
    private Integer childrenCount;

    @Column(name = "youth_count")
    private Integer youthCount;

    @Column(name = "adults_count")
    private Integer adultsCount;

    @Column(name = "seniors_count")
    private Integer seniorsCount;

    // PETS
    @Column(name = "dogs_count")
    private Integer dogsCount;

    @Column(name = "cats_count")
    private Integer catsCount;

    @Column(name = "dog_names", columnDefinition = "TEXT")
    private String dogNames;

    @Column(name = "cat_names", columnDefinition = "TEXT")
    private String catNames;

    @Column(name = "dog_info", columnDefinition = "TEXT")
    private String dogInfo;

    @Column(name = "other_pets")
    private String otherPets;

    // VEHICLES
    @Column(name = "cars_count")
    private Integer carsCount;

    // LIFESTYLE
    @Column(name = "smokers")
    private Boolean smokers;

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

    public String getSecondaryTenantName() {return secondaryTenantName;}
    public TenantEntity setSecondaryTenantName(String secondaryTenantName) {this.secondaryTenantName = secondaryTenantName;return this;}

    public String getSecondaryTenantPhone() {return secondaryTenantPhone;}
    public TenantEntity setSecondaryTenantPhone(String secondaryTenantPhone) {this.secondaryTenantPhone = secondaryTenantPhone;return this;}

    public String getSecondaryTenantEmail() {return secondaryTenantEmail;}
    public TenantEntity setSecondaryTenantEmail(String secondaryTenantEmail) {this.secondaryTenantEmail = secondaryTenantEmail;return this;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public TenantEntity setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;return this;}

    public Boolean getActive() { return active; }
    public TenantEntity setActive(Boolean active) { this.active = active; return this; }

    public String getEmergencyContactName() { return emergencyContactName; }
    public TenantEntity setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
        return this;
    }

    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public TenantEntity setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
        return this;
    }

    public String getNotes() { return notes; }
    public TenantEntity setNotes(String notes) { this.notes = notes; return this; }

    // HOUSEHOLD
    public Integer getChildrenCount() { return childrenCount; }
    public void setChildrenCount(Integer childrenCount) { this.childrenCount = childrenCount; }

    public Integer getYouthCount() { return youthCount; }
    public void setYouthCount(Integer youthCount) { this.youthCount = youthCount; }

    public Integer getAdultsCount() { return adultsCount; }
    public void setAdultsCount(Integer adultsCount) { this.adultsCount = adultsCount; }

    public Integer getSeniorsCount() { return seniorsCount; }
    public void setSeniorsCount(Integer seniorsCount) { this.seniorsCount = seniorsCount; }

    // PETS
    public Integer getDogsCount() { return dogsCount; }
    public void setDogsCount(Integer dogsCount) { this.dogsCount = dogsCount; }

    public Integer getCatsCount() { return catsCount; }
    public void setCatsCount(Integer catsCount) { this.catsCount = catsCount; }

    public String getDogInfo() {return dogInfo;}
    public TenantEntity setDogInfo(String dogInfo) {this.dogInfo = dogInfo;return this;}

    public String getDogNames() { return dogNames; }
    public void setDogNames(String dogNames) { this.dogNames = dogNames; }

    public String getCatNames() { return catNames; }
    public void setCatNames(String catNames) { this.catNames = catNames; }

    public String getOtherPets() { return otherPets; }
    public void setOtherPets(String otherPets) { this.otherPets = otherPets; }

    // VEHICLES
    public Integer getCarsCount() { return carsCount; }
    public void setCarsCount(Integer carsCount) { this.carsCount = carsCount; }

    // LIFESTYLE
    public Boolean getSmokers() { return smokers; }
    public void setSmokers(Boolean smokers) { this.smokers = smokers; }
}