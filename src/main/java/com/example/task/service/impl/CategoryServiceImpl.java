package com.example.task.service.impl;

import com.example.task.exception.EntityNotFoundException;
import com.example.task.model.binding.CategoryBindingModel;
import com.example.task.model.entity.CategoryEntity;
import com.example.task.model.view.CategoryViewModel;
import com.example.task.repository.CategoryRepository;
import com.example.task.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not exist.";
    public static final String CATEGORY_REMOVED = "Category with id %d removed.";

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public List<CategoryViewModel> getAllCategories() {

        List<CategoryEntity> allCategories = this.categoryRepository.findAll();

        return modelMapper.map(allCategories, new TypeToken<List<CategoryViewModel>>() {
        }.getType());
    }

    @Override
    public CategoryViewModel addCategory(CategoryBindingModel newCategory) {

        CategoryEntity createdCategory = new CategoryEntity();
        createdCategory.setName(newCategory.getName())
                .setProducts(new ArrayList<>());


        CategoryEntity savedCategory = this.categoryRepository.save(createdCategory);


        return this.modelMapper.map(savedCategory, CategoryViewModel.class);
    }

    @Override
    @Transactional
    public CategoryViewModel getCategoryById(Long id) {
        CategoryEntity category = this.categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE));


        return this.modelMapper.map(category, CategoryViewModel.class);


    }

    @Override
    @Transactional
    public CategoryViewModel changeCategoryName(Long categoryId, String categoryName) {

        CategoryEntity categoryForNameChanging = this.categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE));

        categoryForNameChanging.setName(categoryName);
        CategoryEntity savedCategory = this.categoryRepository.save(categoryForNameChanging);


        return this.modelMapper.map(savedCategory, CategoryViewModel.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        CategoryEntity categoryEntity = this.categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_MESSAGE));

        this.categoryRepository.delete(categoryEntity);

        return String.format(CATEGORY_REMOVED,categoryEntity.getId());
    }


}
