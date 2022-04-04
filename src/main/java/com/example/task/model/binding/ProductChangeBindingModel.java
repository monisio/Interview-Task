package com.example.task.model.binding;

import com.example.task.validation.annotations.CategoryExists;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductChangeBindingModel {

    @NotNull(message = "Product id is required.")
    private Long id;

    @NotBlank(message = "Name cannot be empty or blank.")
    @Size(min = 3, message = "Required minimal product name length is 3 characters.")
    private String name;

    @NotBlank
    @Size(min = 10 , max = 12000, message = "Description text length required minimum 10 maximum 12000" )
    private String description;

    @Positive(message = "Price cannot be 0 or negative.")
    private BigDecimal price;

    @NotNull(message = "Category id is required")
    @CategoryExists(message = "Category not exists")
    private Long categoryId;


    public ProductChangeBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public ProductChangeBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductChangeBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductChangeBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductChangeBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public ProductChangeBindingModel setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }
}
