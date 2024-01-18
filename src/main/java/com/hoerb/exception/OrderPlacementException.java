package com.hoerb.exception;

public class OrderPlacementException extends Exception {
    public OrderPlacementException() {
    }

    public OrderPlacementException(String message) {
        super(message);
    }

    public OrderPlacementException(String message, Throwable cause) {
        super(message, cause);
    }
}
