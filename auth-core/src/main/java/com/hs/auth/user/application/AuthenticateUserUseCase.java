package com.hs.auth.user.application;

import com.hs.auth.user.application.dto.AuthenticateUserCommand;
import com.hs.auth.user.domain.User;
import com.hs.auth.user.domain.UserId;
import com.hs.auth.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@Transactional
public class AuthenticateUserUseCase {
    
    private final UserRepository userRepository;
    
    public AuthenticateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //캐시처리 필요
    public User execute(AuthenticateUserCommand command) {
        return userRepository.findByProviderAndEmail(command.getProvider(), command.getEmail())
                .orElseGet(() -> registerNewUser(command));
    }
    
    private User registerNewUser(AuthenticateUserCommand command) {
        User newUser = new User(
                UserId.generate(),
                command.getEmail(),
                command.getProvider(),
                ZonedDateTime.now()
        );
        return userRepository.save(newUser);
    }
}