package com.cornerstone.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UnitInspectionDto {

    private Long id;

    private Long unitId;
    private Long tenantId;

    private String inspectionType;
    private LocalDate inspectionDate;

    private String inspectorOne;
    private String inspectorTwo;

    private Boolean memberPresent = false;
    private Boolean handbook = false;
    private Boolean emergencyInspection = false;
    private Boolean photos = false;

    private String petsCat;
    private String petsDog;
    private String dogBreed;
    private String miceIssues;

    private String remarks;

    private String inspectorSignature;
    private String occupantSignature;

    private List<UnitInspectionItemDto> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public UnitInspectionDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUnitId() {
        return unitId;
    }

    public UnitInspectionDto setUnitId(Long unitId) {
        this.unitId = unitId;
        return this;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public UnitInspectionDto setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getInspectionType() {
        return inspectionType;
    }

    public UnitInspectionDto setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
        return this;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public UnitInspectionDto setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate = inspectionDate;
        return this;
    }

    public String getInspectorOne() {
        return inspectorOne;
    }

    public UnitInspectionDto setInspectorOne(String inspectorOne) {
        this.inspectorOne = inspectorOne;
        return this;
    }

    public String getInspectorTwo() {
        return inspectorTwo;
    }

    public UnitInspectionDto setInspectorTwo(String inspectorTwo) {
        this.inspectorTwo = inspectorTwo;
        return this;
    }

    public Boolean getMemberPresent() {
        return memberPresent;
    }

    public UnitInspectionDto setMemberPresent(Boolean memberPresent) {
        this.memberPresent = memberPresent;
        return this;
    }

    public Boolean getHandbook() {
        return handbook;
    }

    public UnitInspectionDto setHandbook(Boolean handbook) {
        this.handbook = handbook;
        return this;
    }

    public Boolean getEmergencyInspection() {
        return emergencyInspection;
    }

    public UnitInspectionDto setEmergencyInspection(Boolean emergencyInspection) {
        this.emergencyInspection = emergencyInspection;
        return this;
    }

    public Boolean getPhotos() {
        return photos;
    }

    public UnitInspectionDto setPhotos(Boolean photos) {
        this.photos = photos;
        return this;
    }

    public String getPetsCat() {
        return petsCat;
    }

    public UnitInspectionDto setPetsCat(String petsCat) {
        this.petsCat = petsCat;
        return this;
    }

    public String getPetsDog() {
        return petsDog;
    }

    public UnitInspectionDto setPetsDog(String petsDog) {
        this.petsDog = petsDog;
        return this;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public UnitInspectionDto setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
        return this;
    }

    public String getMiceIssues() {
        return miceIssues;
    }

    public UnitInspectionDto setMiceIssues(String miceIssues) {
        this.miceIssues = miceIssues;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public UnitInspectionDto setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public String getInspectorSignature() {
        return inspectorSignature;
    }

    public UnitInspectionDto setInspectorSignature(String inspectorSignature) {
        this.inspectorSignature = inspectorSignature;
        return this;
    }

    public String getOccupantSignature() {
        return occupantSignature;
    }

    public UnitInspectionDto setOccupantSignature(String occupantSignature) {
        this.occupantSignature = occupantSignature;
        return this;
    }

    public List<UnitInspectionItemDto> getItems() {
        return items;
    }

    public UnitInspectionDto setItems(List<UnitInspectionItemDto> items) {
        this.items = items;
        return this;
    }
}