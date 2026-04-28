package com.cornerstone.repository;

import com.cornerstone.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitJpaRepository extends JpaRepository<UnitEntity, Long> {
}