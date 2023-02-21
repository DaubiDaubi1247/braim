package ru.alex.braim.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdValidation implements ConstraintValidator<Id, Long> {
    @Override
    public void initialize(Id constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {

        return aLong != null && aLong > 0;
    }
}
