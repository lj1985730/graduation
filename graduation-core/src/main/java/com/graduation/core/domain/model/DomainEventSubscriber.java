package com.graduation.core.domain.model;

/**
 * 领域事件订阅方接口
 * @author Liu Jun
 * @since v1.0.0
 */
public interface DomainEventSubscriber<T extends DomainEvent> {

    /**
     * 返回领域事件类型
     * @return 领域事件类型
     */
    Class<T> subscribedToEventType();

    /**
     * 处理领域事件
     * @param domainEvent 领域事件
     */
    void handleEvent(T domainEvent);
}
