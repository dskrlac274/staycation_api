package com.agency04.devcademy.staycation.controller;

import com.agency04.devcademy.staycation.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IdDoesNotExist.class)
    protected ResponseEntity<String> handleIdDoesNotExist(RuntimeException e, WebRequest request) {
        return new ResponseEntity("Element with that ID does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IdAlreadyExists.class)
    protected ResponseEntity<String> handleIdAlreadyExists(RuntimeException e, WebRequest request) {
        return new ResponseEntity("Element with that ID already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<String> handleBadRequestException(RuntimeException e, WebRequest request) {
        return new ResponseEntity("Bad request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<String> handleIOException(RuntimeException e, WebRequest request) {
        return new ResponseEntity("Input Output Exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(NoSuchElement.class)
    protected ResponseEntity<String> handleNoSuchElementException(RuntimeException e, WebRequest request) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler({NotFoundException.class, NoAvailableOptionsException.class})
    protected ResponseEntity<String> handleNotFoundException(RuntimeException e, WebRequest request) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.PAYMENT_REQUIRED);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
