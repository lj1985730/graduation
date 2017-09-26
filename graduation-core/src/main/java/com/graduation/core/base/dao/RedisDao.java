package com.graduation.core.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * redis读写帮助类
 * @author Liu Jun
 * @version 2016-9-23 11:42:33
 */
@Repository
public final class RedisDao {

	@Resource(name = "redisTemplate")
	private ValueOperations<String, Object> valueOperations;

	@Resource(name = "redisTemplate")
	private HashOperations<String, Serializable, Object> hashOperations;
	
	/**
	 * 判断缓存中是否有对应的key
	 * @param key 待判定的key
	 * @return true 有；false 没有
	 */
	public Boolean exist(final String key) {
		return valueOperations.getOperations().hasKey(key);
	}
	
	/**
	 * 判断缓存中是否有对应的key
	 * @param key 待判定的key
	 * @param hashKey 待判定的hashKey
	 * @return true 有；false 没有
	 */
	public Boolean existHash(final String key, final Serializable hashKey) {
		return hashOperations.hasKey(key, hashKey);
	}

	/**
	 * 获取缓存中全部的key
	 * @return 全部的key
	 */
	public Set<String> listKeys() {
		return valueOperations.getOperations().keys("*");
	}
	
	/**
	 * 获取缓存中key对应全部的hashKey
	 * @return 全部的hashKey
	 */
	public Set<Serializable> listHashKeys(final String key) {
		return hashOperations.keys(key);
	}
	
	/**
	 * 读取缓存
	 * @param key 缓存键
	 * @return 缓存值
	 */
	public Object getValue(final String key) {
		return valueOperations.get(key);
	}
	
	/**
	 * 读取缓存
	 * @param key 缓存键
	 * @return 缓存值
	 */
	public Object getValue(final String key, final Serializable hashKey) {
		return hashOperations.get(key, hashKey);
	}
	
	/**
	 * 读取缓存
	 * @param key 缓存键
	 * @return 缓存值
	 */
	public Map<Serializable, Object> getHashMap(final String key) {
		return hashOperations.entries(key);
	}
	
	/**
	 * 加入缓存
	 * @param key 缓存键
	 */
	public void setValue(final String key, final Object value) {
		valueOperations.set(key, value);
	}
	
	/**
	 * 加入缓存
	 * @param key	缓存键
	 * @param value 缓存值
	 * @param expireSecond 有效秒数
	 */
	public void setValue(final String key, final Object value, final Integer expireSecond) {
		valueOperations.set(key, value, expireSecond, TimeUnit.SECONDS);
	}
	
	/**
	 * 加入缓存
	 * @param key 缓存键
	 * @param hashKey 缓存Hash键
	 * @param value 缓存值
	 */
	public void setHashValue(final String key, final Serializable hashKey, final Object value) {
		hashOperations.put(key, hashKey, value);
	}
	
	/**
	 * 加入缓存
	 * @param key 缓存键
	 * @param hashKey 缓存Hash键
	 * @param value 缓存值
	 * @param expireSecond 有效秒数
	 */
	public void setHashValue(final String key, final Serializable hashKey, final Object value, final Integer expireSecond) {
		hashOperations.put(key, hashKey, value);
		hashOperations.getOperations().expire(key, expireSecond, TimeUnit.SECONDS);
	}
	
	/**
	 * 加入缓存
	 * @param key 缓存键
	 * @param hashMap 缓存Hash键
	 */
	public void setHashAll(final String key, final Map<? extends Serializable, Object> hashMap) {
		hashOperations.putAll(key, hashMap);
	}
	
	/**
	 * 加入缓存
	 * @param key 缓存键
	 * @param hashMap 缓存Hash对象
	 * @param expireSecond 有效秒数
	 */
	public void setHashAll(final String key, final Map<? extends Serializable, Object> hashMap, final Integer expireSecond) {
		hashOperations.putAll(key, hashMap);
		hashOperations.getOperations().expire(key, expireSecond, TimeUnit.SECONDS);
	}
	
	/**
	 * 删除缓存
	 * @param key 缓存键
	 */
	public void removeValue(final String key) {
		valueOperations.getOperations().delete(key);
	}
	
	/**
	 * 删除缓存
	 * @param keyList 缓存键集合
	 */
	public void removeValue(final Collection<String> keyList) {
		valueOperations.getOperations().delete(keyList);
	}

	/**
	 * 删除缓存
	 * @param key	缓存键
	 * @param hashKeys	缓存Hash键
	 */
	public void removeValue(final String key, final Object... hashKeys) {
		hashOperations.delete(key, hashKeys);
	}
	
	public ValueOperations<String, Object> getValueOperations() {
		return valueOperations;
	}

	public HashOperations<String, Serializable, Object> getHashOperations() {
		return hashOperations;
	}
}