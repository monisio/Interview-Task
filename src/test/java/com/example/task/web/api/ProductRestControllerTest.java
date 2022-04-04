package com.example.task.web.api;

import com.example.task.model.binding.CategoryBindingModel;
import com.example.task.model.binding.ProductBindingModel;
import com.example.task.model.binding.ProductChangeBindingModel;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductRestControllerTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        CategoryEntity category1 = new CategoryEntity().setName("test1").setProducts(new ArrayList<>());
        CategoryEntity category2 = new CategoryEntity().setName("test2").setProducts(new ArrayList<>());
        CategoryEntity saved1 = categoryRepository.save(category1);
        CategoryEntity saved2 = categoryRepository.save(category2);


        ProductEntity productModel1 = new ProductEntity().setCategory(saved1).setName("test product1").setDescription("Placeholder description").setPrice(BigDecimal.valueOf(102.301));
        ProductEntity productModel2 = new ProductEntity().setCategory(saved1).setName("test product2").setDescription("Placeholder description2").setPrice(BigDecimal.valueOf(99.99));

        this.productRepository.save(productModel1);
        this.productRepository.save(productModel2);
    }

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    void testListProductsReturnListOfAllProducts() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/product")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$.[0].name").value("test product1")).
                andExpect(jsonPath("$.[1].name").value("test product2"));
    }

    @Test
    void testAddProductAddingProductsToCategory() throws Exception {
        ProductBindingModel product = new ProductBindingModel().setCategoryId(2L).setName("test product3").setDescription("Placeholder description").setPrice(BigDecimal.valueOf(44.20)).setCategoryId(2L);


        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/product").content(new ObjectMapper().writeValueAsString(product)).contentType(MediaType.APPLICATION_JSON)).

                andExpect(status().isCreated()).
                andExpect(jsonPath("$.name").value("test product3")).
                andExpect(jsonPath("$.id").value("3")).
                andExpect(jsonPath("$.categoryId").value("2"));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/category/2")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name").value("test2"))
                .andExpect(jsonPath("$.products", hasSize(1)))
                .andExpect(jsonPath("$.products[0].name").value("test product3"));
    }

    @Test
    void testGetProductReturnsCorrectProduct() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/product/1")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name").value("test product1")).
                andExpect(jsonPath("$.categoryId").value("1")).
                andExpect(jsonPath("$.price").value("102.301"));
    }

    @Test
    void testGetProductReturnsReturnErrorOnNotExistingProduct() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/product/11")).
                andExpect(status().isNotFound()).
                andExpect(jsonPath("$").value("Product with id 11 not exist."));
    }


    @Test
    void testUpdateProductForCorrectlyUpdateProduct() throws Exception {
        ProductChangeBindingModel product = new ProductChangeBindingModel()
                .setId(1L)
                .setCategoryId(2L)
                .setDescription("updated description")
                .setName("updated name")
                .setPrice(BigDecimal.valueOf(99.19));

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/product").content(new ObjectMapper().writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name").value("updated name")).
                andExpect(jsonPath("$.id").value("1")).
                andExpect(jsonPath("$.description").value("updated description")).
                andExpect(jsonPath("$.categoryId").value("2"));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/category/2")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name").value("test2"))
                .andExpect(jsonPath("$.products", hasSize(1)))
                .andExpect(jsonPath("$.products[0].name").value("updated name"));

    }


    @Test
    void testUpdateProductReturnErrorsOnValidationFailure() throws Exception {
        ProductChangeBindingModel product = new ProductChangeBindingModel()
                .setId(null)
                .setCategoryId(6L)
                .setDescription("a")
                .setName("ab")
                .setPrice(BigDecimal.valueOf(-99.19));

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/product").content(new ObjectMapper().writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest()).
                andDo(print()).
                andExpect(jsonPath("$.fieldErrors.id").value("Product id is required.")).
                andExpect(jsonPath("$.fieldErrors.name").value("Required minimal product name length is 3 characters.")).
                andExpect(jsonPath("$.fieldErrors.categoryId").value("Category not exists")).
                andExpect(jsonPath("$.fieldErrors.description").value("Description text length required minimum 10 maximum 12000")).
                andExpect(jsonPath("$.fieldErrors.price").value("Price cannot be 0 or negative."));

    }

    @Test
    void testUpdateProductReturnErrorOnInvalidProductId() throws Exception {
        ProductChangeBindingModel product = new ProductChangeBindingModel()
                .setId(12L)
                .setCategoryId(2L)
                .setDescription("product description")
                .setName("new name")
                .setPrice(BigDecimal.valueOf(99.19));

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/product").content(new ObjectMapper().writeValueAsString(product))
                        .contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound()).
                andDo(print()).
                andExpect(jsonPath("$").value("Product with id 12 not exist."));

    }

    @Test
    void deleteProduct() {
    }
}