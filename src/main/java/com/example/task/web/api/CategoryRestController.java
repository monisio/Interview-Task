package com.example.task.web.api;


import com.example.task.model.binding.CategoryBindingModel;
import com.example.task.model.view.CategoryViewModel;
import com.example.task.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryRestController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryViewModel>> listCategories() {

        return ResponseEntity.ok(this.categoryService.getAllCategories());

    }


    @PostMapping("/category")
    public ResponseEntity<CategoryViewModel> addCategory(
            @RequestBody @Valid CategoryBindingModel newCategory
    ) {



        CategoryViewModel createdCategory = this.categoryService.addCategory(newCategory);
        URI newCategoryUri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(String.format("/api/category/%d", createdCategory.getId()))
                .toUriString());


        return ResponseEntity.created(newCategoryUri).body(createdCategory);

    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryViewModel> getCategory(@PathVariable Long categoryId) {

        CategoryViewModel category = this.categoryService.getCategoryById(categoryId);

        return ResponseEntity.ok(category);
    }

    @PatchMapping("/category/{categoryId}")
    public ResponseEntity<CategoryViewModel> updateCategoryName(@PathVariable Long categoryId, @RequestBody @Valid CategoryBindingModel category) {

        CategoryViewModel changedCategory = this.categoryService.changeCategoryName(categoryId, category.getName());
        return ResponseEntity.ok(changedCategory);


    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){

       String message = this.categoryService.deleteCategory(categoryId);

      return ResponseEntity.ok(message);
    }

}
