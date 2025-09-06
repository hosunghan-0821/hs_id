package com.hs.auth.authentication.jwt.application;

import com.hs.auth.authentication.jwt.application.dto.GenerateTokenCommand;
import com.hs.auth.authentication.jwt.domain.JwtToken;
import com.hs.auth.authentication.jwt.domain.JwtTokenPair;
import com.hs.auth.authentication.jwt.domain.port.RefreshTokenRepository;
import com.hs.auth.authentication.jwt.domain.port.TokenGenerator;
import com.hs.auth.authentication.jwt.domain.port.TokenValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GenerateTokenUseCase {

    private final TokenGenerator tokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenValidator tokenValidator;
    private final long refreshTokenValidityInSeconds;

    public GenerateTokenUseCase(
            TokenGenerator tokenGenerator,
            RefreshTokenRepository refreshTokenRepository,
            TokenValidator tokenValidator,
            @Value("${jwt.refresh-token.validity-seconds:604800}") long refreshTokenValidityInSeconds
    ) {
        this.tokenGenerator = tokenGenerator;
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenValidator = tokenValidator;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
    }

    public JwtTokenPair execute(GenerateTokenCommand command) {
        JwtToken accessToken = tokenGenerator.generateAccessToken(command.getUser(), command.getServiceName());
        JwtToken refreshToken = tokenGenerator.generateRefreshToken(command.getUser(), command.getServiceName());

        // refresh token에서 tokenId 추출하여 Redis에 저장
        String tokenId = tokenValidator.extractTokenId(refreshToken.getToken());
        
        refreshTokenRepository.save(
                command.getUser().getId().getValue(),
                command.getServiceName(),
                tokenId,
                refreshToken.getToken(),
                refreshTokenValidityInSeconds
        );

        return new JwtTokenPair(accessToken, refreshToken);
    }
}
