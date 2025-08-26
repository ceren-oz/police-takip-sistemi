package com.example.customer_management.mapper;

import com.example.customer_management.domain.AracBilgileri;

public class AracBilgileriMapper {

    public static AracBilgileriDTO toDto(AracBilgileri entity) {
        if (entity == null) {
            return null;
        }

        AracBilgileriDTO dto = new AracBilgileriDTO();
        dto.setAracId(entity.getAracId());
        dto.setPlakaNo(entity.getPlakaNo());
        dto.setKullanimSekli(entity.getKullanimSekli());
        dto.setMarkaModel(entity.getMarkaModel());
        dto.setAracTipi(entity.getAracTipi());
        dto.setModelYili(entity.getModelYili());
        dto.setMotorHacmi(entity.getMotorHacmi());
        dto.setYakitTuru(entity.getYakitTuru());
        dto.setSasiNo(entity.getSasiNo());
        dto.setAracDegeri(entity.getAracDegeri());
        dto.setHasarSayisi(entity.getHasarSayisi());
        dto.setOlusturulmaTarihi(entity.getOlusturulmaTarihi());
        dto.setGuncellemeTarihi(entity.getGuncellemeTarihi());
        return dto;
    }

    public static AracBilgileri toEntity(AracBilgileriDTO dto) {
        if (dto == null) {
            return null;
        }

        AracBilgileri entity = new AracBilgileri();
        entity.setAracId(dto.getAracId());
        entity.setPlakaNo(dto.getPlakaNo());
        entity.setKullanimSekli(dto.getKullanimSekli());
        entity.setMarkaModel(dto.getMarkaModel());
        entity.setAracTipi(dto.getAracTipi());
        entity.setModelYili(dto.getModelYili());
        entity.setMotorHacmi(dto.getMotorHacmi());
        entity.setYakitTuru(dto.getYakitTuru());
        entity.setSasiNo(dto.getSasiNo());
        entity.setAracDegeri(dto.getAracDegeri());
        entity.setHasarSayisi(dto.getHasarSayisi());
        entity.setOlusturulmaTarihi(dto.getOlusturulmaTarihi());
        entity.setGuncellemeTarihi(dto.getGuncellemeTarihi());
        return entity;
    }
}
