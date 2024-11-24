package com.fog.e_commerce.exception;

public class InvalidInputException extends RuntimeException {

    private final String field;

    // Constructor with message and field name
    public InvalidInputException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
