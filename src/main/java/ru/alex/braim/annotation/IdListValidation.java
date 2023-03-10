package ru.alex.braim.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class IdListValidation implements ConstraintValidator<IdList, List<Long>> {
    private List<Long> longs;

    @Override
    public void initialize(IdList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> longs, ConstraintValidatorContext constraintValidatorContext) {
        if (longs == null) {
            return true;
        }

        return longs.size() > 0 && longs.stream()
                .noneMatch(el -> el == null || el <= 0);
    }

}
