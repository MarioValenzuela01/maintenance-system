package com.cornerstone.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "work_order_time_logs")
public class WorkOrderTimeLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrderEntity workOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUserEntity user;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "minutes_worked", nullable = false)
    private Integer minutesWorked;

    @Column(length = 2000)
    private String notes;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public WorkOrderTimeLogEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public WorkOrderEntity getWorkOrder() {
        return workOrder;
    }

    public WorkOrderTimeLogEntity setWorkOrder(WorkOrderEntity workOrder) {
        this.workOrder = workOrder;
        return this;
    }

    public UnitEntity getUnit() {
        return unit;
    }

    public WorkOrderTimeLogEntity setUnit(UnitEntity unit) {
        this.unit = unit;
        return this;
    }

    public AppUserEntity getUser() {
        return user;
    }

    public WorkOrderTimeLogEntity setUser(AppUserEntity user) {
        this.user = user;
        return this;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public WorkOrderTimeLogEntity setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
        return this;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public WorkOrderTimeLogEntity setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public WorkOrderTimeLogEntity setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public Integer getMinutesWorked() {
        return minutesWorked;
    }

    public WorkOrderTimeLogEntity setMinutesWorked(Integer minutesWorked) {
        this.minutesWorked = minutesWorked;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public WorkOrderTimeLogEntity setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public WorkOrderTimeLogEntity setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }
}