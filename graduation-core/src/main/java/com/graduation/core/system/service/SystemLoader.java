package com.graduation.core.system.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Lazy(false)
public class SystemLoader implements InitializingBean {

    @Resource
    private SystemConfigService service;

    /**
     * 定时任务线程池
     */
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    /**
     * 系统配置集合
     */
    static Map<String, String> configMap = new HashMap<>();

    /**
     * 获取系统配置
     * @param key   配置键
     * @param defaultValue  默认值
     * @return 系统配置
     */
    public static String getConfigValue(String key, String defaultValue) {
        String val = configMap.get(key);
        if (StringUtils.isBlank(val)) {
            return defaultValue;
        } else {
            return val;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //每十分钟刷新一次系统配置.
        executorService.scheduleWithFixedDelay(() -> service.loadSystemConfig(), 0, 600, TimeUnit.SECONDS);
    }

    /**
     * 关闭配置自动刷新服务.
     */
    @PreDestroy
    public static void shutdown() {
        executorService.shutdown();
    }
}