package com.cornerstone.repository;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.entity.LeaseEntity;
import com.cornerstone.mapper.LeaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LeaseRepositoryAdapter implements LeaseRepository {

    private final LeaseJpaRepository jpaRepository;
    private final LeaseMapper mapper;

    public LeaseRepositoryAdapter(LeaseJpaRepository jpaRepository, LeaseMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<LeaseDto> getAll() {
        return mapper.toDTO(jpaRepository.findAll());
    }

    @Override
    public Optional<LeaseDto> get(Long id) {
        return mapper.toDTO(jpaRepository.findById(id));
    }

    @Override
    public LeaseDto save(LeaseDto lease) {
        LeaseEntity savedEntity = jpaRepository.save(mapper.toEntity(lease));
        return mapper.toDTO(savedEntity);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }
}