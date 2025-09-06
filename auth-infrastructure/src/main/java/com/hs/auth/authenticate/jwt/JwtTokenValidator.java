package com.hs.auth.authenticate.jwt;

import com.hs.auth.authentication.jwt.domain.JwtClaimType;
import com.hs.auth.authentication.jwt.domain.port.TokenValidator;
import com.hs.auth.common.OAuth2Provider;
import com.hs.auth.common.util.TimeUtils;
import com.hs.auth.user.domain.User;
import com.hs.auth.user.domain.UserId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtTokenValidator implements TokenValidator {

    private final SecretKey secretKey;

    public JwtTokenValidator(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    @Override
    public boolean isExpired(String token) {
        try {
            Claims claims = extractClaims(token);
            ZonedDateTime expirationTime = TimeUtils.fromDate(claims.getExpiration());
            ZonedDateTime currentTime = TimeUtils.nowUtc();
            return expirationTime.isBefore(currentTime);
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            return true;
        }
    }

    @Override
    public User extractUser(String token) {
        Claims claims = extractClaims(token);
        
        String userId = claims.getSubject();
        String email = claims.get(JwtClaimType.EMAIL.getClaimName(), String.class);
        String provider = claims.get(JwtClaimType.PROVIDER.getClaimName(), String.class);
        
        return new User(
                UserId.of(userId),
                email,
                OAuth2Provider.valueOf(provider.toUpperCase()),
                TimeUtils.nowUtc()
        );
    }

    @Override
    public String extractSubject(String token) {
        return extractClaims(token).getSubject();
    }

    @Override
    public String extractServiceName(String token) {
        return extractClaims(token).get(JwtClaimType.SERVICE_NAME.getClaimName(), String.class);
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.", e);
        }
    }
}