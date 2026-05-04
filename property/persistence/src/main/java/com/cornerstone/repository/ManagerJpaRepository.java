package com.cornerstone.repository;

import com.cornerstone.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerJpaRepository extends JpaRepository<ManagerEntity, Long> {
}