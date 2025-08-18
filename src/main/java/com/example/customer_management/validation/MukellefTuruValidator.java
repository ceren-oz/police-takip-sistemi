package com.example.customer_management.validation;

import com.example.customer_management.mapper.MusteriDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MukellefTuruValidator implements ConstraintValidator<MukellefTuruConstraint, MusteriDTO> {
    @Override
    public boolean isValid(MusteriDTO dto, ConstraintValidatorContext context) {
        if (dto == null || hasCommonNulls(dto)) {
            return false;
        }

        return switch (dto.getMukellefTuru()) {
            case GERCEK -> isValidGercek(dto);
            case TUZEL  -> isValidTuzel(dto);
            default     -> false;
        };
    }

    private boolean hasCommonNulls(MusteriDTO dto) {
        return dto.getMukellefTuru() == null
                || dto.getUyruk() == null
                || dto.getUlke() == null
                || dto.getOzelMusteriMi() == null
                || dto.getSgkKullaniyorMu() == null;
    }

    private boolean isValidGercek(MusteriDTO dto) {
        return dto.getTcNo() != null
                && dto.getAd() != null
                && dto.getSoyad() != null
                && dto.getAnaAd() != null
                && dto.getBabaAd() != null
                && dto.getDogumYeri() != null
                && dto.getDogumTarihi() != null
                && dto.getCinsiyet() != null
                && dto.getMeslek() != null
                && dto.getEgitimDurumu() != null;
    }

    private boolean isValidTuzel(MusteriDTO dto) {
        return dto.getVergiNo() != null
                && dto.getSirketUnvani() != null
                && dto.getSirketTuru() != null
                && dto.getSektor() != null;
    }
}
