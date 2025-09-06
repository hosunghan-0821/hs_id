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
            String cleanToken = cleanToken(token);
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(cleanToken);
            return true;
        } catch (JwtException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public boolean isExpired(String token) {
        try {
            String cleanToken = cleanToken(token);
            Claims claims = extractClaims(cleanToken);
            ZonedDateTime expirationTime = TimeUtils.fromDate(claims.getExpiration());
            ZonedDateTime currentTime = TimeUtils.nowUtc();
            return expirationTime.isBefore(currentTime);
        } catch (JwtException e) {
            throw new IllegalArgumentException(e.getMessage());
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
        String cleanToken = cleanToken(token);
        return extractClaims(cleanToken).getSubject();
    }

    @Override
    public String extractServiceName(String token) {
        String cleanToken = cleanToken(token);
        return extractClaims(cleanToken).get(JwtClaimType.SERVICE_NAME.getClaimName(), String.class);
    }

    @Override
    public String extractTokenId(String token) {
        String cleanToken = cleanToken(token);
        return extractClaims(cleanToken).get(JwtClaimType.TOKEN_ID.getClaimName(), String.class);
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

    private String cleanToken(String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return authorization;
    }
}