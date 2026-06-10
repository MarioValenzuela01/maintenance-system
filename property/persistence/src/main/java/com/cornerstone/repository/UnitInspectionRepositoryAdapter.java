package com.cornerstone.repository;

import com.cornerstone.dto.UnitInspectionDto;
import com.cornerstone.dto.UnitInspectionItemDto;
import com.cornerstone.entity.UnitInspectionEntity;
import com.cornerstone.entity.UnitInspectionItemEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UnitInspectionRepositoryAdapter implements UnitInspectionRepository {

    private final UnitInspectionJpaRepository jpaRepository;

    public UnitInspectionRepositoryAdapter(UnitInspectionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<UnitInspectionDto> getAll() {
        return toDtoList(jpaRepository.findAllByOrderByInspectionDateDesc());
    }

    @Override
    public List<UnitInspectionDto> search(Long unitId, String inspectionType) {
        boolean hasUnit = unitId != null;
        boolean hasType = inspectionType != null && !inspectionType.isBlank();

        if (hasUnit && hasType) {
            return toDtoList(jpaRepository.findByUnitIdAndInspectionTypeContainingIgnoreCaseOrderByInspectionDateDesc(
                    unitId,
                    inspectionType
            ));
        }

        if (hasUnit) {
            return toDtoList(jpaRepository.findByUnitIdOrderByInspectionDateDesc(unitId));
        }

        if (hasType) {
            return toDtoList(jpaRepository.findByInspectionTypeContainingIgnoreCaseOrderByInspectionDateDesc(inspectionType));
        }

        return getAll();
    }

    @Override
    public Optional<UnitInspectionDto> get(Long id) {
        return jpaRepository.findById(id).map(this::toDto);
    }

    @Override
    public UnitInspectionDto save(UnitInspectionDto inspection) {
        UnitInspectionEntity entity = toEntity(inspection);
        UnitInspectionEntity saved = jpaRepository.save(entity);
        return toDto(saved);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    private List<UnitInspectionDto> toDtoList(List<UnitInspectionEntity> entities) {
        List<UnitInspectionDto> result = new ArrayList<>();

        for (UnitInspectionEntity entity : entities) {
            result.add(toDto(entity));
        }

        return result;
    }

    private UnitInspectionDto toDto(UnitInspectionEntity entity) {
        UnitInspectionDto dto = new UnitInspectionDto()
                .setId(entity.getId())
                .setUnitId(entity.getUnitId())
                .setTenantId(entity.getTenantId())
                .setInspectionType(entity.getInspectionType())
                .setInspectionDate(entity.getInspectionDate())
                .setInspectorOne(entity.getInspectorOne())
                .setInspectorTwo(entity.getInspectorTwo())
                .setMemberPresent(Boolean.TRUE.equals(entity.getMemberPresent()))
                .setHandbook(Boolean.TRUE.equals(entity.getHandbook()))
                .setEmergencyInspection(Boolean.TRUE.equals(entity.getEmergencyInspection()))
                .setPhotos(Boolean.TRUE.equals(entity.getPhotos()))
                .setPetsCat(entity.getPetsCat())
                .setPetsDog(entity.getPetsDog())
                .setDogBreed(entity.getDogBreed())
                .setMiceIssues(entity.getMiceIssues())
                .setRemarks(entity.getRemarks())
                .setInspectorSignature(entity.getInspectorSignature())
                .setOccupantSignature(entity.getOccupantSignature());

        List<UnitInspectionItemDto> items = new ArrayList<>();

        if (entity.getItems() != null) {
            for (UnitInspectionItemEntity item : entity.getItems()) {
                items.add(toItemDto(item));
            }
        }

        dto.setItems(items);
        return dto;
    }

    private UnitInspectionItemDto toItemDto(UnitInspectionItemEntity entity) {
        return new UnitInspectionItemDto()
                .setId(entity.getId())
                .setArea(entity.getArea())
                .setItemName(entity.getItemName())
                .setInCondition(entity.getInCondition())
                .setOutCondition(entity.getOutCondition())
                .setStatus(entity.getStatus())
                .setNotes(entity.getNotes());
    }

    private UnitInspectionEntity toEntity(UnitInspectionDto dto) {
        UnitInspectionEntity entity = new UnitInspectionEntity()
                .setId(dto.getId())
                .setUnitId(dto.getUnitId())
                .setTenantId(dto.getTenantId())
                .setInspectionType(dto.getInspectionType())
                .setInspectionDate(dto.getInspectionDate())
                .setInspectorOne(dto.getInspectorOne())
                .setInspectorTwo(dto.getInspectorTwo())
                .setMemberPresent(Boolean.TRUE.equals(dto.getMemberPresent()))
                .setHandbook(Boolean.TRUE.equals(dto.getHandbook()))
                .setEmergencyInspection(Boolean.TRUE.equals(dto.getEmergencyInspection()))
                .setPhotos(Boolean.TRUE.equals(dto.getPhotos()))
                .setPetsCat(dto.getPetsCat())
                .setPetsDog(dto.getPetsDog())
                .setDogBreed(dto.getDogBreed())
                .setMiceIssues(dto.getMiceIssues())
                .setRemarks(dto.getRemarks())
                .setInspectorSignature(dto.getInspectorSignature())
                .setOccupantSignature(dto.getOccupantSignature());

        List<UnitInspectionItemEntity> items = new ArrayList<>();

        if (dto.getItems() != null) {
            for (UnitInspectionItemDto itemDto : dto.getItems()) {
                UnitInspectionItemEntity itemEntity = toItemEntity(itemDto);
                itemEntity.setInspection(entity);
                items.add(itemEntity);
            }
        }

        entity.setItems(items);
        return entity;
    }

    private UnitInspectionItemEntity toItemEntity(UnitInspectionItemDto dto) {
        return new UnitInspectionItemEntity()
                .setId(dto.getId())
                .setArea(dto.getArea())
                .setItemName(dto.getItemName())
                .setInCondition(dto.getInCondition())
                .setOutCondition(dto.getOutCondition())
                .setStatus(dto.getStatus())
                .setNotes(dto.getNotes());
    }
}