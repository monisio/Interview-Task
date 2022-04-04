package com.example.task.web.api.advice;

import com.example.task.exception.EntityNotFoundException;
import com.example.task.validation.ApiError;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiErrors = new ApiError(HttpStatus.BAD_REQUEST);


        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> apiErrors.addFieldError(fieldError.getField(), fieldError.getDefaultMessage()));


        return handleExceptionInternal(ex, apiErrors, headers, apiErrors.getStatus(), request);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> onObjectNotFoundException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);

    }

}
