package ru.alex.braim.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IdListValidation.class})
public @interface IdList {
    String message() default "list elements cant be empty or <= 0";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
