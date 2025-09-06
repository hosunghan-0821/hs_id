package com.hs.auth.user.application;

import com.hs.auth.user.application.dto.RegisterServiceUserCommand;
import com.hs.auth.user.domain.ServiceUser;
import com.hs.auth.user.domain.port.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterServiceUserUseCase {

    private final UserRepository userRepository;

    public RegisterServiceUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public ServiceUser execute(RegisterServiceUserCommand command) {
        return userRepository.findServiceUserByUserIdAndServiceName(
                command.getUserId(),
                command.getServiceName()
        ).orElseGet(() -> {
            ServiceUser newServiceUser = new ServiceUser(
                    command.getUserId(),
                    command.getServiceName()
            );
            return userRepository.saveServiceUser(newServiceUser);
        });
    }
}
