package com.hs.auth.common.event;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}