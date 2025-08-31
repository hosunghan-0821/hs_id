package com.hs.auth.jwt.domain;

import com.hs.auth.user.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtTokenGenerator {

    private final SecretKey secretKey;
    private final long accessTokenValidityInSeconds;
    private final long refreshTokenValidityInSeconds;

    public JwtTokenGenerator(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token.validity-seconds:3600}") long accessTokenValidityInSeconds,
            @Value("${jwt.refresh-token.validity-seconds:604800}") long refreshTokenValidityInSeconds
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
    }

    public JwtToken generateAccessToken(User user, String serviceName) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expiration = now.plusSeconds(accessTokenValidityInSeconds);

        String token = Jwts.builder()
                .subject(user.getId().getValue())
                .claim("email", user.getEmail())
                .claim("provider", user.getProvider().getValue())
                .claim("service_name", serviceName)
                .issuedAt(Date.from(now.toInstant()))
                .expiration(Date.from(expiration.toInstant()))
                .signWith(secretKey)
                .compact();

        return new JwtToken(token, TokenType.ACCESS, now, expiration);
    }

    public JwtToken generateRefreshToken(User user, String serviceName) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expiration = now.plusSeconds(refreshTokenValidityInSeconds);

        String token = Jwts.builder()
                .subject(user.getId().getValue())
                .claim("service_name", serviceName)
                .issuedAt(Date.from(now.toInstant()))
                .expiration(Date.from(expiration.toInstant()))
                .signWith(secretKey)
                .compact();

        return new JwtToken(token, TokenType.REFRESH, now, expiration);
    }
}
