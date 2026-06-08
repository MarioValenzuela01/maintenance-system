package com.cornerstone.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class WorkOrderTimeLogDto {

    private Long id;

    private Long workOrderId;
    private Long unitId;
    private String unitNumber;
    private String unitDisplayLabel;

    private Long userId;
    private String username;
    private String userFullName;

    private LocalDate workDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private Integer minutesWorked;

    private String notes;
    private LocalDateTime createdDate;

    public Long getId() {
        return id;
    }

    public WorkOrderTimeLogDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public WorkOrderTimeLogDto setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
        return this;
    }

    public Long getUnitId() {
        return unitId;
    }

    public WorkOrderTimeLogDto setUnitId(Long unitId) {
        this.unitId = unitId;
        return this;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public WorkOrderTimeLogDto setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
        return this;
    }

    public String getUnitDisplayLabel() {
        return unitDisplayLabel;
    }

    public WorkOrderTimeLogDto setUnitDisplayLabel(String unitDisplayLabel) {
        this.unitDisplayLabel = unitDisplayLabel;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public WorkOrderTimeLogDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public WorkOrderTimeLogDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public WorkOrderTimeLogDto setUserFullName(String userFullName) {
        this.userFullName = userFullName;
        return this;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public WorkOrderTimeLogDto setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
        return this;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public WorkOrderTimeLogDto setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public WorkOrderTimeLogDto setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public Integer getMinutesWorked() {
        return minutesWorked;
    }

    public WorkOrderTimeLogDto setMinutesWorked(Integer minutesWorked) {
        this.minutesWorked = minutesWorked;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public WorkOrderTimeLogDto setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public WorkOrderTimeLogDto setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }
}