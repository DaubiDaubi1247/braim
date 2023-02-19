package ru.alex.braim.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IdValidation.class})
public @interface Id {
    String message() default "id must be not null and great than 0";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
