package com.hs.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Login request")
public class LoginRequest {
    
    @Schema(description = "Username", example = "admin")
    private String username;
    
    @Schema(description = "Password", example = "password")
    private String password;

    public String getUsername() { 
        return username; 
    }
    
    public void setUsername(String username) { 
        this.username = username; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }
}