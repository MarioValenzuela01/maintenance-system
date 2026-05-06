package com.cornerstone.mapper;

import com.cornerstone.dto.TenantDto;
import com.cornerstone.entity.TenantEntity;
import org.springframework.stereotype.Component;

@Component
public class TenantMapper implements EntityMapper<TenantDto, TenantEntity> {

    @Override
    public TenantDto toDTO(TenantEntity entity) {
        if (entity == null) return null;

        TenantDto dto = new TenantDto();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhoneNumber());
        dto.setActive(entity.getActive());

        dto.setEmergencyContactName(entity.getEmergencyContactName());
        dto.setEmergencyContactPhone(entity.getEmergencyContactPhone());
        dto.setNotes(entity.getNotes());

        dto.setChildrenCount(entity.getChildrenCount());
        dto.setYouthCount(entity.getYouthCount());
        dto.setAdultsCount(entity.getAdultsCount());
        dto.setSeniorsCount(entity.getSeniorsCount());

        dto.setDogsCount(entity.getDogsCount());
        dto.setCatsCount(entity.getCatsCount());
        dto.setDogInfo(entity.getDogInfo());
        dto.setOtherPets(entity.getOtherPets());

        dto.setCarsCount(entity.getCarsCount());
        dto.setSmokers(entity.getSmokers());

        return dto;
    }

    @Override
    public TenantEntity toEntity(TenantDto dto) {
        if (dto == null) return null;

        TenantEntity entity = new TenantEntity()
                .setId(dto.getId())
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setEmail(dto.getEmail())
                .setPhoneNumber(dto.getPhone())
                .setActive(dto.getActive())
                .setEmergencyContactName(dto.getEmergencyContactName())
                .setEmergencyContactPhone(dto.getEmergencyContactPhone())
                .setNotes(dto.getNotes());

        entity.setChildrenCount(dto.getChildrenCount());
        entity.setYouthCount(dto.getYouthCount());
        entity.setAdultsCount(dto.getAdultsCount());
        entity.setSeniorsCount(dto.getSeniorsCount());

        entity.setDogsCount(dto.getDogsCount());
        entity.setCatsCount(dto.getCatsCount());
        entity.setDogInfo(dto.getDogInfo());
        entity.setOtherPets(dto.getOtherPets());

        entity.setCarsCount(dto.getCarsCount());
        entity.setSmokers(dto.getSmokers());

        return entity;
    }
}