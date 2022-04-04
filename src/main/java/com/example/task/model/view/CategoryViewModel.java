package com.example.task.model.view;


import java.util.List;

public class CategoryViewModel {
    private Long id;
    private String name;
    private List<ProductViewModel> products ;

    public CategoryViewModel() {
    }



    public String getName() {
        return name;
    }

    public CategoryViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public Long getId() {
        return id;
    }

    public CategoryViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public List<ProductViewModel> getProducts() {
        return products;
    }

    public CategoryViewModel setProducts(List<ProductViewModel> products) {
        this.products = products;
        return this;
    }
}
