package com.hs.auth.jwt.application;

import com.hs.auth.jwt.application.dto.GenerateTokenCommand;
import com.hs.auth.jwt.domain.JwtToken;
import com.hs.auth.jwt.domain.JwtTokenGenerator;
import com.hs.auth.jwt.domain.JwtTokenPair;
import com.hs.auth.jwt.domain.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenerateTokenUseCase {

    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenValidityInSeconds;

    public GenerateTokenUseCase(
            JwtTokenGenerator jwtTokenGenerator,
            RefreshTokenRepository refreshTokenRepository,
            @Value("${jwt.refresh-token.validity-seconds:604800}") long refreshTokenValidityInSeconds
    ) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
    }

    public JwtTokenPair execute(GenerateTokenCommand command) {
        JwtToken accessToken = jwtTokenGenerator.generateAccessToken(command.getUser(), command.getServiceName());
        JwtToken refreshToken = jwtTokenGenerator.generateRefreshToken(command.getUser(), command.getServiceName());

        refreshTokenRepository.save(
                command.getUser().getId().getValue(),
                command.getServiceName(),
                refreshToken.getToken(),
                refreshTokenValidityInSeconds
        );

        return new JwtTokenPair(accessToken, refreshToken);
    }
}
