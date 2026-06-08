package com.cornerstone.repository;

import com.cornerstone.entity.WorkOrderEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkOrderJpaRepository extends JpaRepository<WorkOrderEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"unit", "assignedTo", "createdBy"})
    List<WorkOrderEntity> findAll();

    @EntityGraph(attributePaths = {"unit", "assignedTo", "createdBy"})
    List<WorkOrderEntity> findByAssignedToUsernameOrderByCreatedDateDesc(String username);
}