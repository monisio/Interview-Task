package com.example.task.service;

import com.example.task.model.binding.ProductBindingModel;
import com.example.task.model.binding.ProductChangeBindingModel;
import com.example.task.model.view.ProductViewModel;

import java.util.List;

public interface ProductService {
    List<ProductViewModel> getAllProducts();

    ProductViewModel addProduct(ProductBindingModel newProduct);

    ProductViewModel getProductById(Long productId);

    ProductViewModel changeProduct(ProductChangeBindingModel product);

    String deleteProduct(Long productId);
}
