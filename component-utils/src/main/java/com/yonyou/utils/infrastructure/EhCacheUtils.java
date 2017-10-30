package com.yonyou.utils.infrastructure;


import com.graduation.core.base.util.SpringContextUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Cache工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class EhCacheUtils {

	private static CacheManager cacheManager = SpringContextUtils.getBean(CacheManager.class);

	public static final String SYSTEM_CACHE = "SYSTEM_CACHE";

	/**
	 * 获取系统缓存
	 * @param key 缓存KEY
	 * @return 系统缓存值
	 */
	public static Object get(String key) {
		return get(SYSTEM_CACHE, key);
	}
	
	/**
	 * 写入系统缓存
	 * @param key 缓存KEY
	 */
	public static void put(String key, Object value) {
		put(SYSTEM_CACHE, key, value);
	}
	
	/**
	 * 从系统缓存中移除
	 * @param key 缓存KEY
	 */
	public static void remove(String key) {
		remove(SYSTEM_CACHE, key);
	}
	
	/**
	 * 获取缓存
	 * @param cacheName 缓存名称
	 * @param key 缓存KEY
	 * @return 缓存
	 */
	public static Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element == null ? null : element.getObjectValue();
	}

	/**
	 * 写入缓存
	 * @param cacheName 缓存名称
	 * @param key 缓存KEY
	 * @param value 缓存值
	 */
	public static void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}

	/**
	 * 从缓存中移除
	 * @param cacheName 缓存名称
	 * @param key 缓存KEY
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}
	
	/**
	 * 获得一个Cache，没有则创建一个。
	 * @param cacheName 缓存名称
	 * @return 缓存
	 */
	public static Cache getCache(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null){
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}
	
}
