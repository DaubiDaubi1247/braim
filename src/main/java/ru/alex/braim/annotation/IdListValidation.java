package ru.alex.braim.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class IdListValidation implements ConstraintValidator<IdList, List<Long>> {
    @Override
    public void initialize(IdList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> longs, ConstraintValidatorContext constraintValidatorContext) {
        return longs.stream()
                .anyMatch(el -> el == null || el <= 0);
    }

}
