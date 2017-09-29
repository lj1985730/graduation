package com.graduation.core.base.util;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

/**
 * Spring运行环境帮助类
 * @author Liu Jun
 *
 */
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * Spring上下文.
     */
    private static ApplicationContext applicationContext;

    /**
     * 从接口实现，由spring在实例化时注入.
     * @param applicationContext Spring上下文
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据名字获取bean。
	 * @param name bean名字
	 * @return bean
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}


	/**
	 * 根据指定的类型获取bean
	 * @param name 类型名
	 * @param requiredType 类型
	 * @return bean
	 */
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * 判断给定bean是否存在
	 * @param name Spring bean对象名
	 * @return 给定bean是否存在
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 是否是单例的
	 * @param name Spring bean对象名
	 * @return 是否是单例
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	/**
	 * 获取bean类型
	 * @param name Spring bean对象名
	 * @return bean类型
	 */
	public static <T> Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	/**
	 * 获取别名
	 * @param name Spring对象名
	 * @return Spring别名
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}
	
	/**
	 * 获取Spring上下文的资源文件输入流。
	 * @param path Spring上下文位置
	 * @return 上下文的资源文件
	 */
	public static Resource getResource(String path) throws IOException {
		return applicationContext.getResource(path);
	}
}
