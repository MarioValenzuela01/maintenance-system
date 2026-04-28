package com.cornerstone.repository;

import com.cornerstone.dto.UnitDto;
import com.cornerstone.entity.UnitEntity;
import com.cornerstone.mapper.UnitMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UnitRepositoryAdapter implements UnitRepository {

    private final UnitJpaRepository jpaRepository;
    private final UnitMapper mapper;

    public UnitRepositoryAdapter(UnitJpaRepository jpaRepository, UnitMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<UnitDto> getAll() {
        return mapper.toDTO(jpaRepository.findAll());
    }

    @Override
    public Optional<UnitDto> get(Long id) {
        return mapper.toDTO(jpaRepository.findById(id));
    }

    @Override
    public UnitDto save(UnitDto unit) {
        UnitEntity savedEntity = jpaRepository.save(mapper.toEntity(unit));
        return mapper.toDTO(savedEntity);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }
}