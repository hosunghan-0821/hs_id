package com.hs.auth.service;

import com.hs.auth.api.dto.OAuth2AuthenticateRequest;
import com.hs.auth.api.dto.OAuth2AuthenticateResponse;
import com.hs.auth.common.exception.OAuth2Exception;
import com.hs.auth.authentication.jwt.application.GenerateTokenUseCase;
import com.hs.auth.authentication.jwt.application.dto.GenerateTokenCommand;
import com.hs.auth.authentication.jwt.domain.JwtTokenPair;
import com.hs.auth.authenticate.oauth2.common.OAuth2Client;
import com.hs.auth.authenticate.oauth2.dto.OAuth2TokenResponse;
import com.hs.auth.authenticate.oauth2.dto.OAuth2UserInfoResponse;
import com.hs.auth.authenticate.oauth2.factory.OAuth2ClientFactory;
import com.hs.auth.authenticate.oauth2.service.OAuth2StateService;
import com.hs.auth.user.application.AuthenticateUserUseCase;
import com.hs.auth.user.application.RegisterServiceUserUseCase;
import com.hs.auth.user.application.dto.AuthenticateUserCommand;
import com.hs.auth.user.application.dto.RegisterServiceUserCommand;
import com.hs.auth.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2AuthenticationService {

    private final OAuth2ClientFactory oauth2ClientFactory;
    private final OAuth2StateService oauth2StateService;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final GenerateTokenUseCase generateTokenUseCase;
    private final RegisterServiceUserUseCase registerServiceUserUseCase;

    public OAuth2AuthenticationService(
            OAuth2ClientFactory oauth2ClientFactory,
            OAuth2StateService oauth2StateService,
            AuthenticateUserUseCase authenticateUserUseCase,
            GenerateTokenUseCase generateTokenUseCase,
            RegisterServiceUserUseCase registerServiceUserUseCase
    ) {
        this.oauth2ClientFactory = oauth2ClientFactory;
        this.oauth2StateService = oauth2StateService;
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.generateTokenUseCase = generateTokenUseCase;
        this.registerServiceUserUseCase = registerServiceUserUseCase;
    }

    public OAuth2AuthenticateResponse authenticate(OAuth2AuthenticateRequest request) {
        String authorizationCode = request.getAuthorizationCode();
        String state = request.getState();

        if (authorizationCode == null || authorizationCode.trim().isEmpty()) {
            throw new IllegalArgumentException("인가 코드가 없습니다.");
        }

        // 1. State 파싱
        OAuth2StateService.OAuth2StateInfo stateInfo = oauth2StateService.parseState(state);

        // 2. OAuth2 토큰 교환
        OAuth2Client oauth2Client = oauth2ClientFactory.getClient(stateInfo.getProvider());
        OAuth2TokenResponse tokenResponse = oauth2Client.exchangeCodeForToken(authorizationCode);

        if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
            throw new OAuth2Exception("토큰 교환에 실패했습니다.", "token_exchange_failed");
        }

        // 3. 사용자 정보 조회
        OAuth2UserInfoResponse userInfo = oauth2Client.getUserInfo(tokenResponse.getAccessToken());
        if (userInfo == null) {
            throw new OAuth2Exception("사용자 정보 조회에 실패했습니다.", "user_info_fetch_failed");
        }

        // 4. 사용자 인증/가입
        AuthenticateUserCommand command = new AuthenticateUserCommand(
                userInfo.getEmail(),
                stateInfo.getProvider()
        );
        User authenticatedUser = authenticateUserUseCase.execute(command);

        // 5. 서비스 가입/확인
        RegisterServiceUserCommand serviceUserCommand = new RegisterServiceUserCommand(
                authenticatedUser.getId(),
                stateInfo.getServiceName()
        );
        registerServiceUserUseCase.execute(serviceUserCommand);

        // 6. JWT 토큰 발급
        GenerateTokenCommand tokenCommand = new GenerateTokenCommand(authenticatedUser, stateInfo.getServiceName());
        JwtTokenPair tokenPair = generateTokenUseCase.execute(tokenCommand);

        // 7. 응답 생성
        OAuth2AuthenticateResponse.UserInfo user = new OAuth2AuthenticateResponse.UserInfo(
                authenticatedUser.getId().getValue(),
                userInfo.getNickname() != null ? userInfo.getNickname() : "",
                authenticatedUser.getEmail()
        );

        return new OAuth2AuthenticateResponse(
                stateInfo.getProvider().getValue(),
                stateInfo.getServiceName(),
                user,
                tokenPair.getAccessToken().getToken(),
                tokenPair.getRefreshToken().getToken()
        );
    }
}
