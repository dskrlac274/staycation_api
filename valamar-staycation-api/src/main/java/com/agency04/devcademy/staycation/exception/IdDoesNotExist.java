package com.agency04.devcademy.staycation.exception;

public class IdDoesNotExist extends RuntimeException {
    public IdDoesNotExist(String errorMessage) {
        super(errorMessage);
    }
}
