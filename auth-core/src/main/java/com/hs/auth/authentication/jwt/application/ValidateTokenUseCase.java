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

    public User extractUserFromToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }
        
        String cleanToken = cleanToken(token);
        if (!tokenValidator.isValid(cleanToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        
        if (tokenValidator.isExpired(cleanToken)) {
            throw new IllegalArgumentException("만료된 토큰입니다.");
        }
        
        return tokenValidator.extractUser(cleanToken);
    }

    private String cleanToken(String token) {
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }
}