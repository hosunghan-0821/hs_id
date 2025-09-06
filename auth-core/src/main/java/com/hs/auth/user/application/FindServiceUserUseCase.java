package com.hs.auth.user.application;

import com.hs.auth.user.domain.ServiceUser;
import com.hs.auth.user.domain.UserId;
import com.hs.auth.user.domain.port.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class FindServiceUserUseCase {

    private final UserRepository userRepository;

    public FindServiceUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ServiceUser execute(UserId userId, String serviceName) {
        Assert.notNull(userId, "사용자 ID가 없습니다.");
        Assert.hasText(serviceName, "서비스 이름이 없습니다.");
        
        return userRepository.findServiceUserByUserIdAndServiceName(userId, serviceName)
                .orElseThrow(() -> new IllegalArgumentException("해당 서비스의 사용자를 찾을 수 없습니다."));
    }
}