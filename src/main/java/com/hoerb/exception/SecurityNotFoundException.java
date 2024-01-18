package com.hoerb.exception;

public class SecurityNotFoundException extends Exception {
    public SecurityNotFoundException() {
        super();
    }

    public SecurityNotFoundException(String message) {
        super(message);
    }

    public SecurityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
