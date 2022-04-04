package com.example.task.validation.validators;


import com.example.task.repository.CategoryRepository;
import com.example.task.validation.annotations.CategoryExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryExistsValidator implements ConstraintValidator<CategoryExists,Long> {

    private final CategoryRepository categoryRepository;

    public CategoryExistsValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return this.categoryRepository.existsById(value);
    }
}
