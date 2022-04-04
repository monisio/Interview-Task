package com.example.task.validation;

import org.springframework.http.HttpStatus;


import java.util.*;

public class ApiError {

    private final HttpStatus status;
    private Map<String, String> fieldErrors;

    public ApiError(HttpStatus status) {
        this.status = status;
        this.fieldErrors= new HashMap<>();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void addFieldError(String fieldName, String errorMessage){
        this.fieldErrors.put(fieldName, errorMessage);
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public ApiError setFieldErrors(Map<String,String> fieldErrors) {
        this.fieldErrors = fieldErrors;
        return this;
    }
}
