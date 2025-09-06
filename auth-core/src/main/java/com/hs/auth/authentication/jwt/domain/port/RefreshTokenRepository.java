package com.hs.auth.authentication.jwt.domain.port;

public interface RefreshTokenRepository {

    void save(String userId, String serviceName, String tokenId, String refreshToken, long ttlSeconds);

    String findByUserIdServiceNameAndTokenId(String userId, String serviceName, String tokenId);

    void deleteByUserIdServiceNameAndTokenId(String userId, String serviceName, String tokenId);

}
