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
        return mapper.toDTO(jpaRepository.findByArchivedFalse());
    }

    @Override
    public Optional<UnitDto> get(Long id) {
        return mapper.toDTO(jpaRepository.findById(id));
    }

    @Override
    public UnitDto save(UnitDto unit) {

        UnitEntity entity;

        if (unit.getId() != null) {
            entity = jpaRepository.findById(unit.getId())
                    .orElseThrow(() -> new RuntimeException("Unit not found"));

            entity.setUnitNumber(unit.getUnitNumber())
                    .setAddress(unit.getAddress())
                    .setStatus(unit.getStatus())
                    .setOwnershipType(unit.getOwnershipType())
                    .setProgramType(unit.getProgramType())
                    .setManagedByCornerstone(unit.getManagedByCornerstone())
                    .setNotes(unit.getNotes())
                    .setBedrooms(unit.getBedrooms())
                    .setBathrooms(unit.getBathrooms())
                    .setFloors(unit.getFloors())
                    .setHasBasement(unit.getHasBasement())
                    .setGoogleMapsUrl(unit.getGoogleMapsUrl())
                    .setDisplayName(unit.getDisplayName());

            if (unit.getArchived() != null) {
                entity.setArchived(unit.getArchived());
            }

        } else {
            entity = mapper.toEntity(unit);

            if (entity.getArchived() == null) {
                entity.setArchived(false);
            }
        }

        UnitEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.findById(id).ifPresent(unit -> {
            unit.setArchived(true);
            jpaRepository.save(unit);
        });
    }

    @Override
    public Optional<UnitDto> getByUnitNumber(String unitNumber) {
        return mapper.toDTO(jpaRepository.findByUnitNumberAndArchivedFalse(unitNumber));
    }

    @Override
    public List<UnitDto> getAllByIds(List<Long> ids) {
        return mapper.toDTO(jpaRepository.findAllById(ids));
    }
}