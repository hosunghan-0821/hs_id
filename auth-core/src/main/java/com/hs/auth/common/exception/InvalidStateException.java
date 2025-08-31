package com.hs.auth.common.exception;

public class InvalidStateException extends RuntimeException {
    public InvalidStateException(String message) {
        super(message);
    }
    
    public InvalidStateException(String message, Throwable cause) {
        super(message, cause);
    }
}