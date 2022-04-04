package com.example.task.web.api;

import com.example.task.model.binding.CategoryBindingModel;
import com.example.task.model.entity.CategoryEntity;
import com.example.task.repository.CategoryRepository;
import com.example.task.service.impl.CategoryServiceImpl;
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

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CategoryRestControllerTest {

    @Autowired
    private  CategoryRepository categoryRepository;

    @Autowired
    private  MockMvc mockMvc;



    @BeforeEach
    public void setUp() {

        CategoryEntity category1 = new CategoryEntity().setName("test1").setProducts(new ArrayList<>());
        CategoryEntity category2 = new CategoryEntity().setName("test2").setProducts(new ArrayList<>());
        categoryRepository.save(category1);
        categoryRepository.save(category2);
    }

    @AfterEach
    public void tearDown() {
        categoryRepository.deleteAll();
    }


    @Test
    public void testGetRequestToListCategoriesReturnListOfCorrectCategories() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/category")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$.[0].name").value("test1")).
                andExpect(jsonPath("$.[1].name").value("test2"));
    }



    @Test
    void testAddCategory() throws Exception {
        CategoryBindingModel category = new CategoryBindingModel().setName("test3");


        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/category").content(new ObjectMapper().writeValueAsString(category)).contentType(MediaType.APPLICATION_JSON)).

                andExpect(status().isCreated()).
                andExpect(jsonPath("$.name").value("test3")).
                andExpect(jsonPath("$.id").value("3"));
    }

    @Test
    void testGetCategoryReturnsCorrectCategory() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/category/1")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.name").value("test1"));
    }

    @Test
    void testGetCategoryReturnNotFoundExceptionOnNotExistingCategoryId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/category/10")).
                andExpect(status().isNotFound()).
                andExpect(jsonPath("$").value(CategoryServiceImpl.CATEGORY_NOT_FOUND_MESSAGE));
    }

    @Test
    void testUpdateCategoryNameChangesName() throws Exception {
        CategoryBindingModel category = new CategoryBindingModel().setName("changed name");
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/category/1")
                        .content(new ObjectMapper().writeValueAsString(category))
                        .contentType(MediaType.APPLICATION_JSON)).

                andExpect(status().isOk()).
                andExpect(jsonPath("$.name").value("changed name"));

    }

    @Test
    void testUpdateCategoryNameReturnsErrorOnNotExistingCategory() throws Exception {
        CategoryBindingModel category = new CategoryBindingModel().setName("changed name");
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/category/10")
                        .content(new ObjectMapper().writeValueAsString(category))
                        .contentType(MediaType.APPLICATION_JSON)).

                andExpect(status().isNotFound()).
                andExpect(jsonPath("$").value(CategoryServiceImpl.CATEGORY_NOT_FOUND_MESSAGE));

    }

    @Test
    void testUpdateCategoryNameReturnsErrorOnValidation() throws Exception {
        CategoryBindingModel category = new CategoryBindingModel().setName("test1");
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/category/10")
                        .content(new ObjectMapper().writeValueAsString(category))
                        .contentType(MediaType.APPLICATION_JSON)).

                andExpect(status().isBadRequest()).
                andExpect(jsonPath("$.fieldErrors.name").value("Category already exists!"));

    }


    @Test
    void testDeleteCategoryRemovesTheCategory() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/category/1")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$").value("Category with id 1 removed."));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/category/1")).
                andExpect(status().isNotFound()).
                andExpect(jsonPath("$").value(CategoryServiceImpl.CATEGORY_NOT_FOUND_MESSAGE));
    }

    @Test
    void testDeleteCategoryReturnsErrorOnNonExistingCategory() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/category/10")).
                andExpect(status().isNotFound()).
                andExpect(jsonPath("$").value(CategoryServiceImpl.CATEGORY_NOT_FOUND_MESSAGE));
    }

}