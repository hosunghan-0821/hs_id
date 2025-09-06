package com.hs.auth.authentication.jwt.application;

import com.hs.auth.authentication.jwt.application.dto.GenerateTokenCommand;
import com.hs.auth.authentication.jwt.domain.JwtToken;
import com.hs.auth.authentication.jwt.domain.JwtTokenPair;
import com.hs.auth.authentication.jwt.domain.port.RefreshTokenRepository;
import com.hs.auth.authentication.jwt.domain.port.TokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GenerateTokenUseCase {

    private final TokenGenerator tokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenValidityInSeconds;

    public GenerateTokenUseCase(
            TokenGenerator tokenGenerator,
            RefreshTokenRepository refreshTokenRepository,
            @Value("${jwt.refresh-token.validity-seconds:604800}") long refreshTokenValidityInSeconds
    ) {
        this.tokenGenerator = tokenGenerator;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenValidityInSeconds = refreshTokenValidityInSeconds;
    }

    public JwtTokenPair execute(GenerateTokenCommand command) {
        JwtToken accessToken = tokenGenerator.generateAccessToken(command.getUser(), command.getServiceName());
        JwtToken refreshToken = tokenGenerator.generateRefreshToken(command.getUser(), command.getServiceName());

        refreshTokenRepository.save(
                command.getUser().getId().getValue(),
                command.getServiceName(),
                refreshToken.getToken(),
                refreshTokenValidityInSeconds
        );

        return new JwtTokenPair(accessToken, refreshToken);
    }
}
