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

        // AGREGADO: mapeamos los campos nuevos de entidad a DTO
        dto.setEmergencyContactName(entity.getEmergencyContactName());
        dto.setEmergencyContactPhone(entity.getEmergencyContactPhone());
        dto.setNotes(entity.getNotes());

        return dto;
    }

    @Override
    public TenantEntity toEntity(TenantDto dto) {
        if (dto == null) return null;

        return new TenantEntity()
                .setId(dto.getId())
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setEmail(dto.getEmail())
                .setPhoneNumber(dto.getPhone())
                .setActive(dto.getActive())
                // AGREGADO: mapeamos los campos nuevos de DTO a entidad
                .setEmergencyContactName(dto.getEmergencyContactName())
                .setEmergencyContactPhone(dto.getEmergencyContactPhone())
                .setNotes(dto.getNotes());
    }
}