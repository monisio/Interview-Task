package com.example.task.validation.annotations;

import com.example.task.validation.validators.CategoryExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CategoryExistsValidator.class)
public @interface CategoryExists {

    String message() default "Not existing category.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
