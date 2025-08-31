package com.hs.auth.authentication.jwt.domain;

public interface RefreshTokenRepository {

    void save(String userId, String serviceName, String refreshToken, long ttlSeconds);

    String findByUserIdAndServiceName(String userId, String serviceName);

    void deleteByUserIdAndServiceName(String userId, String serviceName);

}
