package com.hs.auth.authentication.jwt.domain.port;

import com.hs.auth.user.domain.User;

public interface TokenValidator {
    
    boolean isValid(String token);
    
    boolean isExpired(String token);
    
    User extractUser(String token);
    
    String extractSubject(String token);
    
    String extractServiceName(String token);
    
    String extractTokenId(String token);
}