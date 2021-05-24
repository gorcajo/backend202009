package com.example.demo.exception;

public class IllegalArgumentsException extends RuntimeException {

    public IllegalArgumentsException() {
        super();
    }

    public IllegalArgumentsException(String errorMessage) {
        super(errorMessage);
    }
}
