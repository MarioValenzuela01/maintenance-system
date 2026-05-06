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

    private Boolean active = true;

    // 🔽 HOUSEHOLD
    private Integer childrenCount;
    private Integer youthCount;
    private Integer adultsCount;
    private Integer seniorsCount;

    // 🔽 PETS
    private Integer dogsCount;
    private Integer catsCount;
    private String dogNames;
    private String catNames;

    private String dogInfo;
    private String otherPets;

    // 🔽 VEHICLES
    private Integer carsCount;

    // 🔽 LIFESTYLE
    private Boolean smokers;

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

    public Boolean getActive() { return active; }
    public TenantDto setActive(Boolean active) { this.active = active; return this; }

    // 🔽 HOUSEHOLD
    public Integer getChildrenCount() { return childrenCount; }
    public void setChildrenCount(Integer childrenCount) { this.childrenCount = childrenCount; }

    public Integer getYouthCount() { return youthCount; }
    public void setYouthCount(Integer youthCount) { this.youthCount = youthCount; }

    public Integer getAdultsCount() { return adultsCount; }
    public void setAdultsCount(Integer adultsCount) { this.adultsCount = adultsCount; }

    public Integer getSeniorsCount() { return seniorsCount; }
    public void setSeniorsCount(Integer seniorsCount) { this.seniorsCount = seniorsCount; }

    // 🔽 PETS
    public Integer getDogsCount() { return dogsCount; }
    public void setDogsCount(Integer dogsCount) { this.dogsCount = dogsCount; }

    public Integer getCatsCount() { return catsCount; }
    public void setCatsCount(Integer catsCount) { this.catsCount = catsCount; }

    public String getDogInfo() {return dogInfo;}
    public TenantDto setDogInfo(String dogInfo) {this.dogInfo = dogInfo;return this;}

    public String getOtherPets() { return otherPets; }
    public void setOtherPets(String otherPets) { this.otherPets = otherPets; }

    public String getDogNames() {return dogNames;}
    public TenantDto setDogNames(String dogNames) {this.dogNames = dogNames;return this;}

    public String getCatNames() {return catNames;}
    public TenantDto setCatNames(String catNames) {this.catNames = catNames;return this;}

    // 🔽 VEHICLES
    public Integer getCarsCount() { return carsCount; }
    public void setCarsCount(Integer carsCount) { this.carsCount = carsCount; }

    // 🔽 LIFESTYLE
    public Boolean getSmokers() { return smokers; }
    public void setSmokers(Boolean smokers) { this.smokers = smokers; }
}