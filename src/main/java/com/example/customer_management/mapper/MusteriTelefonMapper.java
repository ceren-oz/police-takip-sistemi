package com.example.customer_management.mapper;
import com.example.customer_management.domain.MusteriTelefon;
import com.example.customer_management.mapper.MusteriTelefonDTO;

public interface MusteriTelefonMapper {

    // Entity -> DTO
    public static MusteriTelefonDTO toDTO(MusteriTelefon entity) {
        if (entity == null) return null;

        MusteriTelefonDTO dto = new MusteriTelefonDTO();
        dto.setId(entity.getId());
        dto.setMusteriId(entity.getMusteri() != null ? entity.getMusteri().getId() : null);
        dto.setIletisimTelefonuMu(entity.getIletisimTelefonuMu());
        dto.setTelefonTipi(entity.getTelefonTipi());
        dto.setUlkeKodu(entity.getUlkeKodu());
        dto.setAlanKodu(entity.getAlanKodu());
        dto.setTelefonNumarasi(entity.getTelefonNumarasi());
        return dto;
    }

    // DTO -> Entity
    public static MusteriTelefon toEntity(MusteriTelefonDTO dto) {
        if (dto == null) return null;

        MusteriTelefon entity = new MusteriTelefon();
        entity.setIletisimTelefonuMu(dto.getIletisimTelefonuMu());
        entity.setTelefonTipi(dto.getTelefonTipi());
        entity.setUlkeKodu(dto.getUlkeKodu());
        entity.setAlanKodu(dto.getAlanKodu());
        entity.setTelefonNumarasi(dto.getTelefonNumarasi());

        // Musteri nesnesini burada oluşturmak yerine, service katmanında setMusteri yapılmalı
        return entity;
    }

}
