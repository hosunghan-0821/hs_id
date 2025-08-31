package com.hs.auth.user.persistence;

import com.hs.auth.common.OAuth2Provider;
import com.hs.auth.user.domain.User;
import com.hs.auth.user.domain.UserId;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "user",
       uniqueConstraints = @UniqueConstraint(columnNames = {"provider", "email"}))
public class UserEntity {
    
    @Id
    @Column(name = "id", length = 36)
    private String id;
    
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 20)
    private OAuth2Provider provider;
    
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;
    
    protected UserEntity() {}
    
    public UserEntity(String id, String email, OAuth2Provider provider, ZonedDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.provider = provider;
        this.createdAt = createdAt;
    }

    public static UserEntity fromDomain(User user) {
        return new UserEntity(
                user.getId().getValue(),
                user.getEmail(),
                user.getProvider(),
                user.getCreatedAt()
        );
    }

    public User toDomain() {
        return new User(
                UserId.of(this.id),
                this.email,
                this.provider,
                this.createdAt
        );
    }
    
    public String getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public OAuth2Provider getProvider() {
        return provider;
    }
    
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
