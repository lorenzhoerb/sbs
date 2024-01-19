package com.hoerb.exception;

public class UnsupportedSecurityException extends RuntimeException {
    public UnsupportedSecurityException() {
        super();
    }

    public UnsupportedSecurityException(String message) {
        super(message);
    }

    public UnsupportedSecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
