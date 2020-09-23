package com.example.bewery.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MvcExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationErrorHandle(ConstraintViolationException ex){
        List<String> errorsList = new ArrayList<>(ex.getConstraintViolations().size());
        ex.getConstraintViolations().forEach(x -> errorsList.add(x.toString()));
        return new ResponseEntity<>(errorsList, HttpStatus.BAD_REQUEST);
    }
}
