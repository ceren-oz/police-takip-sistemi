package com.example.customer_management.validation;

import com.example.customer_management.mapper.MusteriDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MukellefTuruValidator implements ConstraintValidator<MukellefTuruConstraint, MusteriDTO> {
    @Override
    public boolean isValid(MusteriDTO dto, ConstraintValidatorContext context) {
        if (dto.getMukellefTuru() == null) {
            return false;
        }

        switch (dto.getMukellefTuru()) {
            case GERCEK:
                return dto.getTcNo() != null && dto.getAd() != null && dto.getSoyad() != null;
            case TUZEL:
                return dto.getVergiNo() != null && dto.getSirketUnvani() != null;
            default:
                return false;
        }
    }
}
