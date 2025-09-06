package com.hs.auth.authentication.jwt.domain.port;

import com.hs.auth.authentication.jwt.domain.JwtToken;
import com.hs.auth.user.domain.User;

public interface TokenGenerator {
    
    JwtToken generateAccessToken(User user, String serviceName);
    
    JwtToken generateRefreshToken(User user, String serviceName);
}