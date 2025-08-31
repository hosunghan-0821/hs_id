package com.hs.auth.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceUserJpaRepository extends JpaRepository<ServiceUserEntity, String> {
    Optional<ServiceUserEntity> findByUserIdAndServiceName(String userId, String serviceName);
    List<ServiceUserEntity> findByUserId(String userId);
}