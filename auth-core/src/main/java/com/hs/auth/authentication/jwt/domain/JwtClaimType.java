package com.hs.auth.authentication.jwt.domain;

public enum JwtClaimType {
    EMAIL("email"),
    PROVIDER("provider"),
    SERVICE_NAME("service_name"),
    TOKEN_ID("token_id");
    
    private final String claimName;
    
    JwtClaimType(String claimName) {
        this.claimName = claimName;
    }
    
    public String getClaimName() {
        return claimName;
    }
    
    @Override
    public String toString() {
        return claimName;
    }
}