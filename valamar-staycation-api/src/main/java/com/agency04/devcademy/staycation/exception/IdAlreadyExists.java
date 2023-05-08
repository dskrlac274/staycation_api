package com.agency04.devcademy.staycation.exception;

public class IdAlreadyExists extends RuntimeException{
    public IdAlreadyExists(String errorMessage) {
        super(errorMessage);
    }
}
