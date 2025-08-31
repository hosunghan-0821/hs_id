package com.hs.auth.user.domain;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public class ServiceUser {
    private final ServiceUserId id;
    private final UserId userId;
    private final String serviceName;
    private final ZonedDateTime joinedAt;

    public ServiceUser(ServiceUserId id, UserId userId, String serviceName, ZonedDateTime joinedAt) {
        this.id = Objects.requireNonNull(id, "서비스 사용자 ID는 필수입니다.");
        this.userId = Objects.requireNonNull(userId, "사용자 ID는 필수입니다.");
        this.serviceName = Objects.requireNonNull(serviceName, "서비스 이름은 필수입니다.");
        this.joinedAt = Objects.requireNonNull(joinedAt, "가입일시는 필수입니다.");
    }

    public ServiceUser(UserId userId, String serviceName) {
        this(ServiceUserId.of(UUID.randomUUID().toString()), userId, serviceName, ZonedDateTime.now());
    }

    public ServiceUserId getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ZonedDateTime getJoinedAt() {
        return joinedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceUser that = (ServiceUser) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
