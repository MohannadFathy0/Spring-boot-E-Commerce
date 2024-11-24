package com.fog.e_commerce.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);  // Pass message to the parent constructor
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);  // Pass both message and cause to the parent constructor
    }
}
