package com.cornerstone.repository;

import com.cornerstone.dto.WorkOrderTimeLogDto;
import com.cornerstone.entity.WorkOrderTimeLogEntity;
import com.cornerstone.mapper.WorkOrderTimeLogMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class WorkOrderTimeLogRepositoryAdapter implements WorkOrderTimeLogRepository {

    private static final List<String> ACCESSIBLE_UNIT_NUMBERS = List.of(
            "146-1",
            "146-2",
            "146-3",
            "146-4"
    );

    private final WorkOrderTimeLogJpaRepository timeLogJpaRepository;
    private final WorkOrderJpaRepository workOrderJpaRepository;
    private final UnitJpaRepository unitJpaRepository;
    private final AppUserRepository appUserRepository;

    public WorkOrderTimeLogRepositoryAdapter(WorkOrderTimeLogJpaRepository timeLogJpaRepository,
                                             WorkOrderJpaRepository workOrderJpaRepository,
                                             UnitJpaRepository unitJpaRepository,
                                             AppUserRepository appUserRepository) {
        this.timeLogJpaRepository = timeLogJpaRepository;
        this.workOrderJpaRepository = workOrderJpaRepository;
        this.unitJpaRepository = unitJpaRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public List<WorkOrderTimeLogDto> getByWorkOrderId(Long workOrderId) {
        return timeLogJpaRepository.findByWorkOrder_IdOrderByWorkDateDescStartTimeDesc(workOrderId)
                .stream()
                .map(WorkOrderTimeLogMapper::toDto)
                .toList();
    }

    @Override
    public List<WorkOrderTimeLogDto> getAccessibleUnitLogsBetween(LocalDate fromDate, LocalDate toDate) {
        return timeLogJpaRepository.findByUnit_UnitNumberInAndWorkDateBetweenOrderByWorkDateAscStartTimeAsc(
                        ACCESSIBLE_UNIT_NUMBERS,
                        fromDate,
                        toDate
                )
                .stream()
                .map(WorkOrderTimeLogMapper::toDto)
                .toList();
    }

    @Override
    public WorkOrderTimeLogDto save(WorkOrderTimeLogDto dto) {
        WorkOrderTimeLogEntity entity = new WorkOrderTimeLogEntity();

        entity.setWorkOrder(workOrderJpaRepository.getReferenceById(dto.getWorkOrderId()));
        entity.setUnit(unitJpaRepository.getReferenceById(dto.getUnitId()));
        entity.setUser(appUserRepository.getReferenceById(dto.getUserId()));

        entity.setWorkDate(dto.getWorkDate());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setMinutesWorked(dto.getMinutesWorked());
        entity.setNotes(dto.getNotes());

        WorkOrderTimeLogEntity saved = timeLogJpaRepository.save(entity);

        return WorkOrderTimeLogMapper.toDto(saved);
    }

    @Override
    public void updateUnitByWorkOrderId(Long workOrderId, Long unitId) {
        timeLogJpaRepository.updateUnitByWorkOrderId(
                workOrderId,
                unitJpaRepository.getReferenceById(unitId)
        );
    }
}