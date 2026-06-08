package com.cornerstone.repository;

import com.cornerstone.dto.WorkOrderDto;
import com.cornerstone.entity.WorkOrderEntity;
import com.cornerstone.mapper.WorkOrderMapper;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class WorkOrderRepositoryAdapter implements WorkOrderRepository {

    private final WorkOrderJpaRepository workOrderJpaRepository;
    private final UnitJpaRepository unitJpaRepository;
    private final AppUserRepository appUserRepository;

    public WorkOrderRepositoryAdapter(WorkOrderJpaRepository workOrderJpaRepository,
                                      UnitJpaRepository unitJpaRepository,
                                      AppUserRepository appUserRepository) {
        this.workOrderJpaRepository = workOrderJpaRepository;
        this.unitJpaRepository = unitJpaRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public List<WorkOrderDto> getAll() {
        return workOrderJpaRepository.findAll()
                .stream()
                .map(WorkOrderMapper::toDto)
                .sorted(Comparator.comparing(WorkOrderDto::getCreatedDate,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

    @Override
    public List<WorkOrderDto> getByAssignedUsername(String username) {
        return workOrderJpaRepository.findByAssignedToUsernameOrderByCreatedDateDesc(username)
                .stream()
                .map(WorkOrderMapper::toDto)
                .toList();
    }

    @Override
    public Optional<WorkOrderDto> get(Long id) {
        return workOrderJpaRepository.findById(id)
                .map(WorkOrderMapper::toDto);
    }

    @Override
    public WorkOrderDto save(WorkOrderDto dto) {
        WorkOrderEntity entity;

        if (dto.getId() != null) {
            entity = workOrderJpaRepository.findById(dto.getId())
                    .orElse(new WorkOrderEntity());
        } else {
            entity = new WorkOrderEntity();
        }

        entity.setId(dto.getId());
        entity.setWorkType(dto.getWorkType());
        entity.setPriority(dto.getPriority());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());
        entity.setNotes(dto.getNotes());
        entity.setDueDate(dto.getDueDate());
        entity.setCompletedDate(dto.getCompletedDate());

        if (dto.getCreatedDate() != null) {
            entity.setCreatedDate(dto.getCreatedDate());
        }

        if (dto.getUnitId() != null) {
            entity.setUnit(unitJpaRepository.getReferenceById(dto.getUnitId()));
        }

        if (dto.getAssignedToUserId() != null) {
            entity.setAssignedTo(appUserRepository.getReferenceById(dto.getAssignedToUserId()));
        }

        if (dto.getCreatedByUserId() != null) {
            entity.setCreatedBy(appUserRepository.getReferenceById(dto.getCreatedByUserId()));
        }

        WorkOrderEntity saved = workOrderJpaRepository.save(entity);

        return WorkOrderMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        workOrderJpaRepository.deleteById(id);
    }
}