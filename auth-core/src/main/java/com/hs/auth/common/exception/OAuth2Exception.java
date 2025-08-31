package com.hs.auth.common.exception;

public class OAuth2Exception extends RuntimeException {
    private final String errorCode;
    
    public OAuth2Exception(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public OAuth2Exception(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}