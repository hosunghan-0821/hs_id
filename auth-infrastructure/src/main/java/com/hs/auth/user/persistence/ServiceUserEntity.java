package com.hs.auth.user.persistence;

import com.hs.auth.user.domain.ServiceUser;
import com.hs.auth.user.domain.ServiceUserId;
import com.hs.auth.user.domain.UserId;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "service_user",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "service_name"}))
public class ServiceUserEntity {
    
    @Id
    @Column(name = "id", length = 36)
    private String id;
    
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;
    
    @Column(name = "service_name", nullable = false, length = 50)
    private String serviceName;
    
    @Column(name = "joined_at", nullable = false)
    private ZonedDateTime joinedAt;
    
    protected ServiceUserEntity() {}
    
    public ServiceUserEntity(String id, String userId, String serviceName, ZonedDateTime joinedAt) {
        this.id = id;
        this.userId = userId;
        this.serviceName = serviceName;
        this.joinedAt = joinedAt;
    }

    public static ServiceUserEntity fromDomain(ServiceUser serviceUser) {
        return new ServiceUserEntity(
                serviceUser.getId().getValue(),
                serviceUser.getUserId().getValue(),
                serviceUser.getServiceName(),
                serviceUser.getJoinedAt()
        );
    }

    public ServiceUser toDomain() {
        return new ServiceUser(
                ServiceUserId.of(this.id),
                UserId.of(this.userId),
                this.serviceName,
                this.joinedAt
        );
    }
    
    public String getId() {
        return id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public ZonedDateTime getJoinedAt() {
        return joinedAt;
    }
}
