package com.agency04.devcademy.staycation.exception;

public class NoSuchElement extends RuntimeException {
    public NoSuchElement(String errorMessage) {
        super(errorMessage);
    }
}
