package com.cornerstone.repository;

import com.cornerstone.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitJpaRepository extends JpaRepository<UnitEntity, Long> {

    Optional<UnitEntity> findByUnitNumber(String unitNumber);
}