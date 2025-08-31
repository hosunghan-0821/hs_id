package com.hs.auth.user.domain;

import com.hs.auth.common.OAuth2Provider;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    // User 관련
    Optional<User> findByProviderAndEmail(OAuth2Provider provider, String email);
    User save(User user);
    Optional<User> findById(UserId id);
    
    // ServiceUser 관련
    Optional<ServiceUser> findServiceUserByUserIdAndServiceName(UserId userId, String serviceName);
    List<ServiceUser> findServiceUsersByUserId(UserId userId);
    ServiceUser saveServiceUser(ServiceUser serviceUser);
    Optional<ServiceUser> findServiceUserById(ServiceUserId id);
}