package com.cornerstone.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_orders")
public class WorkOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private UnitEntity unit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assigned_to_user_id", nullable = false)
    private AppUserEntity assignedTo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private AppUserEntity createdBy;

    @Column(name = "work_type", nullable = false)
    private String workType;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(length = 2000)
    private String notes;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "completed_date")
    private LocalDateTime completedDate;

    @PrePersist
    public void prePersist() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }

        if (status == null || status.isBlank()) {
            status = "ASSIGNED";
        }

        if (priority == null || priority.isBlank()) {
            priority = "MEDIUM";
        }
    }

    public Long getId() {
        return id;
    }

    public WorkOrderEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public UnitEntity getUnit() {
        return unit;
    }

    public WorkOrderEntity setUnit(UnitEntity unit) {
        this.unit = unit;
        return this;
    }

    public AppUserEntity getAssignedTo() {
        return assignedTo;
    }

    public WorkOrderEntity setAssignedTo(AppUserEntity assignedTo) {
        this.assignedTo = assignedTo;
        return this;
    }

    public AppUserEntity getCreatedBy() {
        return createdBy;
    }

    public WorkOrderEntity setCreatedBy(AppUserEntity createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getWorkType() {
        return workType;
    }

    public WorkOrderEntity setWorkType(String workType) {
        this.workType = workType;
        return this;
    }

    public String getPriority() {
        return priority;
    }

    public WorkOrderEntity setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public WorkOrderEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkOrderEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public WorkOrderEntity setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public WorkOrderEntity setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public WorkOrderEntity setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public WorkOrderEntity setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
        return this;
    }
}