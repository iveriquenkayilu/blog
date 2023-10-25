package com.example.blog.configurations;

import com.example.blog.models.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseModel> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        // Custom logic to handle the exception
        String errorMessage = "Invalid JSON format in the request body: " + ex.getMessage();
        return  ResponseModel.Fail(errorMessage, HttpStatus.BAD_REQUEST) ;
    }

    // You can add more @ExceptionHandler methods for handling other types of exceptions as well.
}
