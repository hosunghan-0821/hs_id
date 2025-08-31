package com.hs.auth.common.exception;

public class TokenExchangeException extends OAuth2Exception {
    public TokenExchangeException(String message) {
        super(message, "token_exchange_failed");
    }
    
    public TokenExchangeException(String message, Throwable cause) {
        super(message, "token_exchange_failed", cause);
    }
}