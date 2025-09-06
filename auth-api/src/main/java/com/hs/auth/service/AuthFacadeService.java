package com.hs.auth.service;

import com.hs.auth.authentication.jwt.domain.port.TokenValidator;
import com.hs.auth.user.application.FindServiceUserUseCase;
import com.hs.auth.user.domain.ServiceUser;
import com.hs.auth.user.domain.User;
import com.hs.auth.user.domain.UserId;
import org.springframework.stereotype.Service;

@Service
public class AuthFacadeService {

    private final TokenValidator tokenValidator;
    private final FindServiceUserUseCase findServiceUserUseCase;

    public AuthFacadeService(TokenValidator tokenValidator,
                             FindServiceUserUseCase findServiceUserUseCase) {
        this.tokenValidator = tokenValidator;
        this.findServiceUserUseCase = findServiceUserUseCase;
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
}