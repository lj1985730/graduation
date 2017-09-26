package com.graduation.core.domain.model;

import java.time.LocalDateTime;

/**
 * 领域事件-接口
 * @author Liu Jun
 * @since v1.0.0
 */
public interface DomainEvent {

    /**
     * 获取事件发生时间
     * @return 事件发生时间
     */
    LocalDateTime occurredOn();
}
