package com.cornerstone.mapper;

import com.cornerstone.dto.LeaseDto;
import com.cornerstone.entity.LeaseEntity;
import org.springframework.stereotype.Component;

@Component
public class LeaseMapper implements EntityMapper<LeaseDto, LeaseEntity> {

    @Override
    public LeaseDto toDTO(LeaseEntity entity) {
        if (entity == null) return null;

        return new LeaseDto()
                .setId(entity.getId())
                .setTenantId(entity.getTenant().getId())
                .setUnitId(entity.getUnit().getId())
                .setStartDate(entity.getStartDate())
                .setEndDate(entity.getEndDate())

                // 🔥 DATOS ECONÓMICOS
                .setRentAmount(entity.getRentAmount())
                .setSubsidyAmount(entity.getSubsidyAmount())
                .setTenantContribution(entity.getTenantContribution())

                // 🔥 COMPOSICIÓN FAMILIAR
                .setAdultsCount(entity.getAdultsCount())
                .setChildrenCount(entity.getChildrenCount())
                .setSeniorsCount(entity.getSeniorsCount())

                // 🔥 OTROS
                .setSmokers(entity.getSmokers())
                .setPetsCount(entity.getPetsCount())
                .setCarsCount(entity.getCarsCount())
                .setProgramNotes(entity.getProgramNotes())

                // 🔥 DISPLAY (LO QUE QUERÍAS)
                .setTenantName(
                        entity.getTenant().getFirstName() + " " +
                                entity.getTenant().getLastName()
                )
                .setUnitNumber(entity.getUnit().getUnitNumber());
    }

    @Override
    public LeaseEntity toEntity(LeaseDto dto) {
        if (dto == null) return null;

        return new LeaseEntity()
                .setId(dto.getId())
                .setStartDate(dto.getStartDate())
                .setEndDate(dto.getEndDate())

                // 🔥 DATOS ECONÓMICOS
                .setRentAmount(dto.getRentAmount())
                .setSubsidyAmount(dto.getSubsidyAmount())
                .setTenantContribution(dto.getTenantContribution())

                // 🔥 COMPOSICIÓN FAMILIAR
                .setAdultsCount(dto.getAdultsCount())
                .setChildrenCount(dto.getChildrenCount())
                .setSeniorsCount(dto.getSeniorsCount())

                // 🔥 OTROS
                .setSmokers(dto.getSmokers())
                .setPetsCount(dto.getPetsCount())
                .setCarsCount(dto.getCarsCount())
                .setProgramNotes(dto.getProgramNotes());

        // 🔴 tenant y unit se asignan fuera del mapper (correcto en tu arquitectura)
    }
}