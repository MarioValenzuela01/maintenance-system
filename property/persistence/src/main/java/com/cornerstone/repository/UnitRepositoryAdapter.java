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
        // CAMBIADO: antes era findAll(), ahora solo trae las no archivadas
        return mapper.toDTO(jpaRepository.findByArchivedFalse());
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
        // CAMBIADO: antes borraba físico, ahora hace soft delete
        jpaRepository.findById(id).ifPresent(unit -> {
            unit.setArchived(true); // AGREGADO: marcamos como archivada
            jpaRepository.save(unit);
        });
    }

    @Override
    public Optional<UnitDto> getByUnitNumber(String unitNumber) {
        // CAMBIADO: solo busca entre las no archivadas
        return mapper.toDTO(jpaRepository.findByUnitNumberAndArchivedFalse(unitNumber));
    }

    @Override
    public List<UnitDto> getAllByIds(List<Long> ids) {
        return mapper.toDTO(jpaRepository.findAllById(ids));
    }
}