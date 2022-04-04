package com.example.task.service.impl;

import com.example.task.exception.EntityNotFoundException;
import com.example.task.model.binding.ProductBindingModel;
import com.example.task.model.binding.ProductChangeBindingModel;
import com.example.task.model.entity.CategoryEntity;
import com.example.task.model.entity.ProductEntity;
import com.example.task.model.view.ProductViewModel;
import com.example.task.repository.CategoryRepository;
import com.example.task.repository.ProductRepository;
import com.example.task.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with id %d not exist.";
    public static final String PRODUCT_REMOVED = "Product with id %d deleted.";

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductViewModel> getAllProducts() {
        return this.modelMapper.map(this.productRepository.findAll(), new TypeToken<List<ProductViewModel>>() {
        }.getType());
    }

    @Override
    @Transactional
    public ProductViewModel addProduct(ProductBindingModel newProduct) {
        CategoryEntity productCategory = this.categoryRepository.findById(newProduct.getCategoryId()).orElseThrow(() -> new EntityNotFoundException(CategoryServiceImpl.CATEGORY_NOT_FOUND_MESSAGE));


        ProductEntity product = new ProductEntity();
        product
                .setName(newProduct.getName())
                .setDescription(newProduct.getDescription())
                .setPrice(newProduct.getPrice())
                .setCategory(productCategory);



        ProductEntity savedProduct = this.productRepository.save(product);


        return modelMapper.map(savedProduct, ProductViewModel.class);
    }

    @Override
    public ProductViewModel getProductById(Long productId) {
        ProductEntity productEntity = this.productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId)));

        return this.modelMapper.map(productEntity, ProductViewModel.class);

    }

    @Override
    @Transactional
    public ProductViewModel changeProduct(ProductChangeBindingModel product) {
        Long productId = product.getId();
        ProductEntity productEntity = this.productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId)));

        productEntity.setName(product.getName()).setDescription(product.getDescription()).setPrice(product.getPrice());

        Long currentCategoryId = productEntity.getCategory().getId();

        if (currentCategoryId.longValue() != product.getCategoryId().longValue()) {

            CategoryEntity newCategory = this.categoryRepository.findById(product.getCategoryId()).orElseThrow(() -> new EntityNotFoundException(CategoryServiceImpl.CATEGORY_NOT_FOUND_MESSAGE));

            productEntity.setCategory(newCategory);

        }

        return this.modelMapper.map(productRepository.save(productEntity),ProductViewModel.class);
    }

    @Override
    public String deleteProduct(Long productId) {
        ProductEntity product = this.productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId)));
        this.productRepository.delete(product);

        return String.format(PRODUCT_REMOVED , productId) ;
    }
}
