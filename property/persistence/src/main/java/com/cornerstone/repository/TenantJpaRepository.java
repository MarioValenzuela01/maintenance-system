package com.cornerstone.repository;

import com.cornerstone.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenantJpaRepository extends JpaRepository<TenantEntity, Long> {

    // Aquí puedes agregar búsquedas personalizadas más adelante si las necesitas.
    // Por ejemplo:
    // boolean existsByEmailIgnoreCase(String email);
    List<TenantEntity> findByActiveTrue(); // 👈 para filtrar activos
}
