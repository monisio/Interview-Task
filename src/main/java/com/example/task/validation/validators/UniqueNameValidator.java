package com.example.task.validation.validators;

import com.example.task.repository.CategoryRepository;
import com.example.task.validation.annotations.UniqueCategoryName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueCategoryName,String> {

    private final CategoryRepository categoryRepository;

    public UniqueNameValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !categoryRepository.existsByName(value);
    }
}
