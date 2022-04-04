package com.example.task.web.api;

import com.example.task.model.binding.CategoryBindingModel;
import com.example.task.model.binding.ProductBindingModel;
import com.example.task.model.entity.CategoryEntity;
import com.example.task.model.entity.ProductEntity;
import com.example.task.repository.CategoryRepository;
import com.example.task.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductRestControllerTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        CategoryEntity category1 = new CategoryEntity().setName("test1").setProducts(new ArrayList<>());
        CategoryEntity category2 = new CategoryEntity().setName("test2").setProducts(new ArrayList<>());
        CategoryEntity saved1 = categoryRepository.save(category1);
        CategoryEntity saved2 = categoryRepository.save(category2);



        ProductEntity productModel1 = new ProductEntity().setCategory(saved1).setName("test product1").setDescription("Placeholder description").setPrice(BigDecimal.valueOf(102.30));
        ProductEntity productModel2 = new ProductEntity().setCategory(saved1).setName("test product2").setDescription("Placeholder description2").setPrice(BigDecimal.valueOf(99.99));

        this.productRepository.save(productModel1);
        this.productRepository.save(productModel2);
    }

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    void listProducts() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/product")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$.[0].name").value("test product1")).
                andExpect(jsonPath("$.[1].name").value("test product2"));
    }

    @Test
    void addProduct() throws Exception {
        ProductBindingModel product = new ProductBindingModel().setCategoryId(2L).setName("test product3").setDescription("Placeholder description").setPrice(BigDecimal.valueOf(44.20));


        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/product").content(new ObjectMapper().writeValueAsString(product)).contentType(MediaType.APPLICATION_JSON)).

                andExpect(status().isCreated()).
                andExpect(jsonPath("$.name").value("test product3")).
                andExpect(jsonPath("$.id").value("3")).
                andExpect(jsonPath("$.categoryId").value("2"));

       /* this.mockMvc.perform(MockMvcRequestBuilders.get("/api/category/2")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name").value("test2"))
                .andExpect(jsonPath("$.products.name").value("test product3"));*/
    }

    @Test
    void getProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}