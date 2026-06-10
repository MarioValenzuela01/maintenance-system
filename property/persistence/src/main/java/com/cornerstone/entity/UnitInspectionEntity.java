package com.cornerstone.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unit_inspections")
public class UnitInspectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="unit_id", nullable = false)
    private Long unitId;

    @Column(name="tenant_id")
    private Long tenantId;

    @Column(name = "inspection_type", nullable = false)
    private String inspectionType;

    @Column(name = "inspection_date", nullable = false)
    private LocalDate inspectionDate;

    @Column(name = "inspector_one")
    private String inspectorOne;

    @Column(name = "inspector_two")
    private String inspectorTwo;

    @Column(name = "member_present")
    private Boolean memberPresent = false;

    private Boolean handbook = false;

    @Column(name = "emergency_inspection")
    private Boolean emergencyInspection = false;

    private Boolean photos = false;

    @Column(name = "pets_cat")
    private String petsCat;

    @Column(name = "pets_dog")
    private String petsDog;

    @Column(name = "dog_breed")
    private String dogBreed;

    @Column(name = "mice_issues", columnDefinition = "TEXT")
    private String miceIssues;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "inspector_signature")
    private String inspectorSignature;

    @Column(name = "occupant_signature")
    private String occupantSignature;

    @OneToMany(
            mappedBy = "inspection",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UnitInspectionItemEntity> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public UnitInspectionEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUnitId() {
        return unitId;
    }

    public UnitInspectionEntity setUnitId(Long unitId) {
        this.unitId = unitId;
        return this;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public UnitInspectionEntity setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getInspectionType() {
        return inspectionType;
    }

    public UnitInspectionEntity setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
        return this;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public UnitInspectionEntity setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate = inspectionDate;
        return this;
    }

    public String getInspectorOne() {
        return inspectorOne;
    }

    public UnitInspectionEntity setInspectorOne(String inspectorOne) {
        this.inspectorOne = inspectorOne;
        return this;
    }

    public String getInspectorTwo() {
        return inspectorTwo;
    }

    public UnitInspectionEntity setInspectorTwo(String inspectorTwo) {
        this.inspectorTwo = inspectorTwo;
        return this;
    }

    public Boolean getMemberPresent() {
        return memberPresent;
    }

    public UnitInspectionEntity setMemberPresent(Boolean memberPresent) {
        this.memberPresent = memberPresent;
        return this;
    }

    public Boolean getHandbook() {
        return handbook;
    }

    public UnitInspectionEntity setHandbook(Boolean handbook) {
        this.handbook = handbook;
        return this;
    }

    public Boolean getEmergencyInspection() {
        return emergencyInspection;
    }

    public UnitInspectionEntity setEmergencyInspection(Boolean emergencyInspection) {
        this.emergencyInspection = emergencyInspection;
        return this;
    }

    public Boolean getPhotos() {
        return photos;
    }

    public UnitInspectionEntity setPhotos(Boolean photos) {
        this.photos = photos;
        return this;
    }

    public String getPetsCat() {
        return petsCat;
    }

    public UnitInspectionEntity setPetsCat(String petsCat) {
        this.petsCat = petsCat;
        return this;
    }

    public String getPetsDog() {
        return petsDog;
    }

    public UnitInspectionEntity setPetsDog(String petsDog) {
        this.petsDog = petsDog;
        return this;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public UnitInspectionEntity setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
        return this;
    }

    public String getMiceIssues() {
        return miceIssues;
    }

    public UnitInspectionEntity setMiceIssues(String miceIssues) {
        this.miceIssues = miceIssues;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public UnitInspectionEntity setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public String getInspectorSignature() {
        return inspectorSignature;
    }

    public UnitInspectionEntity setInspectorSignature(String inspectorSignature) {
        this.inspectorSignature = inspectorSignature;
        return this;
    }

    public String getOccupantSignature() {
        return occupantSignature;
    }

    public UnitInspectionEntity setOccupantSignature(String occupantSignature) {
        this.occupantSignature = occupantSignature;
        return this;
    }

    public List<UnitInspectionItemEntity> getItems() {
        return items;
    }

    public UnitInspectionEntity setItems(List<UnitInspectionItemEntity> items) {
        this.items = items;
        return this;
    }
}