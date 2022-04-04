package com.example.task.service;

import com.example.task.model.binding.CategoryBindingModel;
import com.example.task.model.entity.CategoryEntity;
import com.example.task.model.view.CategoryViewModel;

import java.util.List;

public interface CategoryService {
    List<CategoryViewModel> getAllCategories();

    CategoryViewModel addCategory(CategoryBindingModel newCategory);

    CategoryViewModel getCategoryById(Long id);

    CategoryViewModel changeCategoryName(Long categoryId ,String categoryName);

    String deleteCategory(Long categoryId);


}
