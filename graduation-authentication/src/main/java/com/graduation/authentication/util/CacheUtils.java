package com.graduation.authentication.util;

import com.graduation.core.base.util.SpringContextUtils;
import com.yonyou.utils.infrastructure.EhCacheUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Cache工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class CacheUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);
	private static CacheManager cacheManager = SpringContextUtils.getBean(CacheManager.class);
	
	/**
	 * 获取系统缓存
	 * @param key 缓存KEY
	 * @return 系统缓存
	 */
	public static Object get(String key) {
		return get(EhCacheUtils.SYSTEM_CACHE, key);
	}
	
	/**
	 * 获取系统缓存，null则返回默认值
	 * @param key 缓存KEY
	 * @param defaultValue 默认值
	 * @return 系统缓存
	 */
	public static Object get(String key, Object defaultValue) {
		Object value = get(key);
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 写入系统缓存
	 * @param key 缓存KEY
	 */
	public static void put(String key, Object value) {
		put(EhCacheUtils.SYSTEM_CACHE, key, value);
	}
	
	/**
	 * 从系统缓存中移除
	 * @param key 缓存KEY
	 */
	public static void remove(String key) {
		remove(EhCacheUtils.SYSTEM_CACHE, key);
	}
	
	/**
	 * 获取缓存
	 * @param cacheName 缓存名称
	 * @param key 缓存KEY
	 * @return 缓存
	 */
	public static Object get(String cacheName, String key) {
		return getCache(cacheName).get(getKey(key));
	}
	
	/**
	 * 获取缓存，null则返回默认值
	 * @param cacheName 缓存名称
	 * @param key 缓存KEY
	 * @param defaultValue 默认值
	 * @return 缓存
	 */
	public static Object get(String cacheName, String key, Object defaultValue) {
		Object value = get(cacheName, getKey(key));
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 写入缓存
	 * @param cacheName 缓存名称
	 * @param key 缓存KEY
	 * @param value 缓存
	 */
	public static void put(String cacheName, String key, Object value) {
		getCache(cacheName).put(getKey(key), value);
	}

	/**
	 * 从缓存中移除
	 * @param cacheName 缓存名称
	 * @param key 缓存KEY
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(getKey(key));
	}

	/**
	 * 从缓存中移除所有
	 * @param cacheName 缓存名称
	 */
	public static void removeAll(String cacheName) {
		Cache<String, Object> cache = getCache(cacheName);
		Set<String> keys = cache.keys();
		for (String key : keys) {
			cache.remove(key);
		}
		logger.info("清理缓存： {} => {}", cacheName, keys);
	}
	
	/**
	 * 获取缓存键名，多数据源下增加数据源名称前缀
	 * @param key 原始缓存键名
	 * @return 缓存键名
	 */
	private static String getKey(String key) {
//		String dsName = DataSourceHolder.getDataSourceName();
//		if (StringUtils.isNotBlank(dsName)){
//			return dsName + "_" + key;
//		}
		return key;
	}
	
	/**
	 * 获得一个缓存，没有则显示日志。
	 * @param cacheName 缓存名称
	 * @return 缓存
	 */
	private static Cache<String, Object> getCache(String cacheName){
		Cache<String, Object> cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			throw new RuntimeException("当前系统中没有定义“" + cacheName + "”这个缓存。");
		}
		return cache;
	}

}
