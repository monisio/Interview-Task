package com.example.task.configuration;


import com.example.task.model.entity.ProductEntity;
import com.example.task.model.view.ProductViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(ProductEntity.class, ProductViewModel.class)
                .addMappings(m-> m.map(src-> src.getCategory().getId(),ProductViewModel::setCategoryId))
                .addMappings(m-> m.map(src-> src.getCategory().getName(),ProductViewModel::setCategoryName));

        return modelMapper;
    }



}
