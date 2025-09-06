package com.hs.auth.authentication.jwt.application;

import com.hs.auth.authentication.jwt.domain.port.TokenValidator;
import com.hs.auth.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class ValidateTokenUseCase {

    private final TokenValidator tokenValidator;

    public ValidateTokenUseCase(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    public boolean execute(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        
        String cleanToken = cleanToken(token);
        return tokenValidator.isValid(cleanToken) && !tokenValidator.isExpired(cleanToken);
    }


    private String cleanToken(String token) {
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }
}