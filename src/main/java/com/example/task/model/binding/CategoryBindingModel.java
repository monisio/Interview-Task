package com.example.task.model.binding;


import com.example.task.validation.annotations.UniqueCategoryName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryBindingModel {

    @NotBlank(message = "Name cannot be empty or blank.")
    @Size(min = 3, message = "Required minimal name length is 3 characters.")
    @UniqueCategoryName(message = "Category already exists!")
    private String name;


    public CategoryBindingModel() {
    }


    public String getName() {
        return name;
    }

    public CategoryBindingModel setName(String name) {
        this.name = name;
        return this;
    }
}
