package com.cornerstone.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WorkOrderDto {

    private Long id;

    private Long unitId;
    private String unitNumber;
    private String unitDisplayName;
    private String unitDisplayLabel;

    private Long assignedToUserId;
    private String assignedToUsername;
    private String assignedToFullName;

    private Long createdByUserId;
    private String createdByUsername;
    private String createdByFullName;

    private String workType;
    private String priority;
    private String status;

    private String description;
    private String notes;

    private LocalDateTime createdDate;
    private LocalDate dueDate;
    private LocalDateTime completedDate;

    public Long getId() {
        return id;
    }

    public WorkOrderDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUnitId() {
        return unitId;
    }

    public WorkOrderDto setUnitId(Long unitId) {
        this.unitId = unitId;
        return this;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public WorkOrderDto setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
        return this;
    }

    public String getUnitDisplayName() {
        return unitDisplayName;
    }

    public WorkOrderDto setUnitDisplayName(String unitDisplayName) {
        this.unitDisplayName = unitDisplayName;
        return this;
    }

    public String getUnitDisplayLabel() {
        return unitDisplayLabel;
    }

    public WorkOrderDto setUnitDisplayLabel(String unitDisplayLabel) {
        this.unitDisplayLabel = unitDisplayLabel;
        return this;
    }

    public Long getAssignedToUserId() {
        return assignedToUserId;
    }

    public WorkOrderDto setAssignedToUserId(Long assignedToUserId) {
        this.assignedToUserId = assignedToUserId;
        return this;
    }

    public String getAssignedToUsername() {
        return assignedToUsername;
    }

    public WorkOrderDto setAssignedToUsername(String assignedToUsername) {
        this.assignedToUsername = assignedToUsername;
        return this;
    }

    public String getAssignedToFullName() {
        return assignedToFullName;
    }

    public WorkOrderDto setAssignedToFullName(String assignedToFullName) {
        this.assignedToFullName = assignedToFullName;
        return this;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public WorkOrderDto setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public WorkOrderDto setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
        return this;
    }

    public String getCreatedByFullName() {
        return createdByFullName;
    }

    public WorkOrderDto setCreatedByFullName(String createdByFullName) {
        this.createdByFullName = createdByFullName;
        return this;
    }

    public String getWorkType() {
        return workType;
    }

    public WorkOrderDto setWorkType(String workType) {
        this.workType = workType;
        return this;
    }

    public String getPriority() {
        return priority;
    }

    public WorkOrderDto setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public WorkOrderDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkOrderDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public WorkOrderDto setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public WorkOrderDto setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public WorkOrderDto setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public WorkOrderDto setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
        return this;
    }
}