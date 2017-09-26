package com.graduation.core.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 领域事件发布器
 * @author Liu Jun
 * @since v1.0.0
 */
public class DomainEventPublisher {

    /**
     * 事件消费者集合
     */
    private static final ThreadLocal<List> subscribers = new ThreadLocal<>();

    /**
     * 是否正在发送事件状态
     */
    private static final ThreadLocal<Boolean> publishing = new ThreadLocal<Boolean>() {
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    /**
     * 获取新发布器实例
     * @return 新发布器实例
     */
    public static DomainEventPublisher instance() {
        return new DomainEventPublisher();
    }

    public DomainEventPublisher() {
        super();
    }

    /**
     * 发布领域事件
     * @param event 领域事件
     * @param <T> 领域事件类型
     */
    @SuppressWarnings("unchecked")
    public <T extends DomainEvent> void publish(final T event) {
        if(publishing.get()) {
            return;
        }

        try {
            publishing.set(Boolean.TRUE);
            List<DomainEventSubscriber<T>> registeredSubscribers = subscribers.get();

            if (registeredSubscribers != null) {
                Class<?> eventType = event.getClass();  //事件类型
                //遍历订阅方，检出订阅当前事件类型的部分，触发事件处理方法
                for (DomainEventSubscriber<T> subscriber : registeredSubscribers) {
                    Class<?> subscribedTo = subscriber.subscribedToEventType();
                    if(subscribedTo == eventType ||
                            subscribedTo == DomainEvent.class) {
                        subscriber.handleEvent(event);
                    }
                }
            }
        } finally {
            publishing.set(Boolean.FALSE);
        }
    }

    /**
     * 重置发布器，清空全部订阅方
     * @return 发布器
     */
    public DomainEventPublisher reset() {
        if(!publishing.get()) {
            subscribers.set(null);  //清空全部订阅方
        }
        return this;
    }

    /**
     * 增加订阅
     * @param subscriber 订阅方
     * @param <T> 领域事件类型
     */
    @SuppressWarnings("unchecked")
    public <T extends DomainEvent> void subscribe(DomainEventSubscriber<T> subscriber) {
        if(publishing.get()) {  //发布中禁止增加订阅
            return;
        }

        List<DomainEventSubscriber<T>> registeredSubscribers = subscribers.get();
        if (registeredSubscribers == null) {  //如果订阅集合不存在，创建
            registeredSubscribers = new ArrayList<>();
            subscribers.set(registeredSubscribers);
        }

        registeredSubscribers.add(subscriber);
    }
}
