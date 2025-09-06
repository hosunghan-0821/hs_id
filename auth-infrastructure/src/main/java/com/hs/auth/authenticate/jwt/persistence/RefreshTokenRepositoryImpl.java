package com.hs.auth.authenticate.jwt.persistence;


import com.hs.auth.authentication.jwt.domain.port.RefreshTokenRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private static final String KEY_PREFIX = "refreshToken:";
    private final StringRedisTemplate redisTemplate;

    public RefreshTokenRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String userId, String serviceName, String tokenId, String refreshToken, long ttlSeconds) {
        String key = createKey(userId, serviceName, tokenId);
        redisTemplate.opsForValue().set(key, refreshToken, ttlSeconds, TimeUnit.SECONDS);
    }

    @Override
    public String findByUserIdServiceNameAndTokenId(String userId, String serviceName, String tokenId) {
        return redisTemplate.opsForValue().get(createKey(userId, serviceName, tokenId));
    }

    @Override
    public void deleteByUserIdServiceNameAndTokenId(String userId, String serviceName, String tokenId) {
        redisTemplate.delete(createKey(userId, serviceName, tokenId));
    }

    private String createKey(String userId, String serviceName, String tokenId) {
        return KEY_PREFIX + userId + ":" + serviceName + ":" + tokenId;
    }
}
