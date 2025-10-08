package com.hs.auth.user.persistence;

import com.hs.auth.authentication.oauth2.domain.OAuth2Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByProviderAndEmail(OAuth2Provider provider, String email);
}