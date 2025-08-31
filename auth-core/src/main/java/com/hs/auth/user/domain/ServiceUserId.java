package com.hs.auth.user.domain;

import java.util.Objects;
import java.util.UUID;

public class ServiceUserId {
    private final String value;
    
    private ServiceUserId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("서비스 사용자 ID는 필수입니다.");
        }
        this.value = value;
    }
    
    public static ServiceUserId of(String value) {
        return new ServiceUserId(value);
    }
    
    public static ServiceUserId generate() {
        return new ServiceUserId(UUID.randomUUID().toString());
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceUserId that = (ServiceUserId) o;
        return Objects.equals(value, that.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return "ServiceUserId{" + "value='" + value + '\'' + '}';
    }
}