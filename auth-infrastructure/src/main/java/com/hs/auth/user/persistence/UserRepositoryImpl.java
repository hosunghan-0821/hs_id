package com.hs.auth.user.persistence;

import com.hs.auth.common.OAuth2Provider;
import com.hs.auth.user.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private final UserJpaRepository userJpaRepository;
    private final ServiceUserJpaRepository serviceUserJpaRepository;
    
    public UserRepositoryImpl(UserJpaRepository userJpaRepository, ServiceUserJpaRepository serviceUserJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.serviceUserJpaRepository = serviceUserJpaRepository;
    }
    
    @Override
    public Optional<User> findByProviderAndEmail(OAuth2Provider provider, String email) {
        return userJpaRepository.findByProviderAndEmail(provider, email)
                .map(UserEntity::toDomain);
    }
    
    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity savedEntity = userJpaRepository.save(entity);
        return savedEntity.toDomain();
    }
    
    @Override
    public Optional<User> findById(UserId id) {
        return userJpaRepository.findById(id.getValue())
                .map(UserEntity::toDomain);
    }
    
    // ServiceUser 관련 메서드
    @Override
    public Optional<ServiceUser> findServiceUserByUserIdAndServiceName(UserId userId, String serviceName) {
        return serviceUserJpaRepository.findByUserIdAndServiceName(userId.getValue(), serviceName)
                .map(ServiceUserEntity::toDomain);
    }
    
    @Override
    public List<ServiceUser> findServiceUsersByUserId(UserId userId) {
        return serviceUserJpaRepository.findByUserId(userId.getValue())
                .stream()
                .map(ServiceUserEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public ServiceUser saveServiceUser(ServiceUser serviceUser) {
        ServiceUserEntity entity = ServiceUserEntity.fromDomain(serviceUser);
        ServiceUserEntity savedEntity = serviceUserJpaRepository.save(entity);
        return savedEntity.toDomain();
    }
    
    @Override
    public Optional<ServiceUser> findServiceUserById(ServiceUserId id) {
        return serviceUserJpaRepository.findById(id.getValue())
                .map(ServiceUserEntity::toDomain);
    }
}
