package com.graduation.core.base.util;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * Spring运行环境帮助类
 * @author Liu Jun
 */
public class SpringContextUtils implements ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(SpringContextUtils.class);

    /**
     * Spring上下文.
     */
    private static ApplicationContext applicationContext;

    /**
     * 从接口实现，由spring在实例化时注入.
     * @param applicationContext Spring上下文
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	/**
	 * 根据名字获取bean。
	 * @param name bean名字
	 * @return bean
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		assertContextInjected();
		return (T)applicationContext.getBean(name);
	}

	/**
	 * 根据指定的类型获取bean
	 * @param requiredType 类型
	 * @return bean
	 */
	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 判断给定bean是否存在
	 * @param name Spring bean对象名
	 * @return 给定bean是否存在
	 */
	public static boolean containsBean(String name) {
		assertContextInjected();
		return applicationContext.containsBean(name);
	}

	/**
	 * 是否是单例的
	 * @param name Spring bean对象名
	 * @return 是否是单例
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		assertContextInjected();
		return applicationContext.isSingleton(name);
	}

	/**
	 * 获取bean类型
	 * @param name Spring bean对象名
	 * @return bean类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getType(String name) throws NoSuchBeanDefinitionException {
		assertContextInjected();
		return (Class<T>)applicationContext.getType(name);
	}

	/**
	 * 获取别名
	 * @param name Spring对象名
	 * @return Spring别名
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		assertContextInjected();
		return applicationContext.getAliases(name);
	}
	
	/**
	 * 获取Spring上下文的资源文件输入流。
	 * @param path Spring上下文位置
	 * @return 上下文的资源文件
	 */
	public static Resource getResource(String path) throws IOException {
		assertContextInjected();
		return applicationContext.getResource(path);
	}

	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void assertContextInjected() {
		Validate.validState(applicationContext != null, "applicationContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
	}

	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clearHolder() {
		if (logger.isDebugEnabled()){
			logger.debug("清除SpringContextUtils中的ApplicationContext:" + applicationContext);
		}
		applicationContext = null;
	}
}
