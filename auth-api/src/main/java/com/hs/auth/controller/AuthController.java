package com.hs.auth.controller;

import com.hs.auth.api.AuthApi;
import com.hs.auth.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthApi {

    @Override
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("status", "healthy", "service", "auth-api"));
    }

    @Override
    public ResponseEntity<Map<String, String>> login(LoginRequest request) {
        if ("admin".equals(request.getUsername()) && "password".equals(request.getPassword())) {
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "token", "sample-jwt-token",
                    "username", request.getUsername()
            ));
        }
        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }

    @Override
    public ResponseEntity<Map<String, Object>> getProfile() {
        return ResponseEntity.ok(Map.of(
                "username", "admin",
                "email", "admin@example.com",
                "role", "ADMIN",
                "lastLogin", "2024-01-01T00:00:00"
        ));
    }
}