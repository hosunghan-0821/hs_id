package com.hs.auth.authentication.jwt.application;

import com.hs.auth.authentication.jwt.domain.port.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RefreshTokenQueryService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenQueryService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String findValidToken(String userId, String serviceName, String tokenId, String inputToken) {
        Assert.hasText(userId, "사용자 ID가 없습니다.");
        Assert.hasText(serviceName, "서비스 이름이 없습니다.");
        Assert.hasText(tokenId, "토큰 ID가 없습니다.");
        Assert.hasText(inputToken, "입력 토큰이 없습니다.");
        
        String storedToken = refreshTokenRepository.findByUserIdServiceNameAndTokenId(userId, serviceName, tokenId);
        
        if (storedToken == null) {
            throw new IllegalArgumentException("유효하지 않은 refresh token입니다.");
        }
        
        if (!storedToken.equals(inputToken)) {
            throw new IllegalArgumentException("Refresh token이 일치하지 않습니다.");
        }
        
        return storedToken;
    }

    public void invalidateToken(String userId, String serviceName, String tokenId) {
        Assert.hasText(userId, "사용자 ID가 없습니다.");
        Assert.hasText(serviceName, "서비스 이름이 없습니다.");
        Assert.hasText(tokenId, "토큰 ID가 없습니다.");
        
        refreshTokenRepository.deleteByUserIdServiceNameAndTokenId(userId, serviceName, tokenId);
    }
}