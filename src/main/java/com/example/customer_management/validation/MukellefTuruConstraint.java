package com.example.customer_management.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MukellefTuruValidator.class)
public @interface MukellefTuruConstraint {
    String message() default "Alanlar müşteri tipine göre eksik";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
