package com.hs.auth.jwt.persistence;

import com.hs.auth.jwt.domain.RefreshTokenRepository;
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
    public void save(String userId, String serviceName, String refreshToken, long ttlSeconds) {
        String key = createKey(userId, serviceName);
        redisTemplate.opsForValue().set(key, refreshToken, ttlSeconds, TimeUnit.SECONDS);
    }

    @Override
    public String findByUserIdAndServiceName(String userId, String serviceName) {
        return redisTemplate.opsForValue().get(createKey(userId, serviceName));
    }

    @Override
    public void deleteByUserIdAndServiceName(String userId, String serviceName) {
        redisTemplate.delete(createKey(userId, serviceName));
    }

    private String createKey(String userId, String serviceName) {
        return KEY_PREFIX + userId + ":" + serviceName;
    }
}
