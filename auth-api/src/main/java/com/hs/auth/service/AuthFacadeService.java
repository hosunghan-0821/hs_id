package com.hs.auth.service;

import com.hs.auth.authentication.jwt.application.GenerateTokenUseCase;
import com.hs.auth.authentication.jwt.application.RefreshTokenQueryService;
import com.hs.auth.authentication.jwt.application.dto.GenerateTokenCommand;
import com.hs.auth.authentication.jwt.domain.JwtTokenPair;
import com.hs.auth.authentication.jwt.domain.port.TokenValidator;
import com.hs.auth.user.application.FindServiceUserUseCase;
import com.hs.auth.user.domain.ServiceUser;
import com.hs.auth.user.domain.User;
import com.hs.auth.user.domain.UserId;
import com.hs.auth.user.domain.port.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AuthFacadeService {

    private final TokenValidator tokenValidator;
    private final FindServiceUserUseCase findServiceUserUseCase;
    private final RefreshTokenQueryService refreshTokenQueryService;
    private final UserRepository userRepository;
    private final GenerateTokenUseCase generateTokenUseCase;

    public AuthFacadeService(TokenValidator tokenValidator,
                             FindServiceUserUseCase findServiceUserUseCase,
                             RefreshTokenQueryService refreshTokenQueryService,
                             UserRepository userRepository,
                             GenerateTokenUseCase generateTokenUseCase) {
        this.tokenValidator = tokenValidator;
        this.findServiceUserUseCase = findServiceUserUseCase;
        this.refreshTokenQueryService = refreshTokenQueryService;
        this.userRepository = userRepository;
        this.generateTokenUseCase = generateTokenUseCase;
    }

    public void verifyToken(String accessToken, User user) {
        String serviceName = tokenValidator.extractServiceName(accessToken);
        findServiceUserUseCase.execute(user.getId(), serviceName);
    }

    public ServiceUser getUserInfo(String accessToken, User user) {
         verifyToken(accessToken, user);
         return null;
         //TODO Hosung return 수정
    }

    public JwtTokenPair refreshToken(String refreshToken) {
        Assert.hasText(refreshToken, "Refresh token이 없습니다.");
        
        // 1. Refresh token에서 사용자 정보 추출
        String userId = tokenValidator.extractSubject(refreshToken);
        String serviceName = tokenValidator.extractServiceName(refreshToken);
        String tokenId = tokenValidator.extractTokenId(refreshToken);
        
        // 2. 토큰 검증 및 무효화
        refreshTokenQueryService.findValidToken(userId, serviceName, tokenId, refreshToken);
        refreshTokenQueryService.invalidateToken(userId, serviceName, tokenId);
        
        // 3. 사용자 정보 조회
        User user = userRepository.findById(UserId.of(userId))
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        
        // 4. 새로운 토큰 쌍 생성
        GenerateTokenCommand command = new GenerateTokenCommand(user, serviceName);
        return generateTokenUseCase.execute(command);
    }
}