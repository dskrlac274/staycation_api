package com.agency04.devcademy.staycation.exception;

public class NoAvailableOptionsException extends RuntimeException{
    public NoAvailableOptionsException() {
    }

    public NoAvailableOptionsException(String message) {
        super(message);
    }

    public NoAvailableOptionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
