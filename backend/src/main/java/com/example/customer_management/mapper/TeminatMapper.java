package com.example.customer_management.mapper;

import com.example.customer_management.domain.Teminat;

public class TeminatMapper {

    public static TeminatDTO toDto(Teminat teminat) {
        if (teminat == null) {
            return null;
        }

        TeminatDTO dto = new TeminatDTO();
        dto.setTeminatId(teminat.getTeminatId());
        dto.setTeminatTuru(teminat.getTeminatTuru());
        dto.setTeminatBedeli(teminat.getTeminatBedeli());
        dto.setOlusturulmaTarihi(teminat.getOlusturulmaTarihi());
        dto.setGuncellemeTarihi(teminat.getGuncellemeTarihi());
        return dto;
    }

    public static Teminat toEntity(TeminatDTO dto) {
        if (dto == null) {
            return null;
        }

        Teminat teminat = new Teminat();
        teminat.setTeminatId(dto.getTeminatId());
        teminat.setTeminatTuru(dto.getTeminatTuru());
        teminat.setTeminatBedeli(dto.getTeminatBedeli());
        teminat.setOlusturulmaTarihi(dto.getOlusturulmaTarihi());
        teminat.setGuncellemeTarihi(dto.getGuncellemeTarihi());
        return teminat;
    }
}
