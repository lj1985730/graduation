package com.graduation.core.base.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具集，解码出存储的信息。
 * 身份校验机制：
 * 1.登陆后获得验证通过，形成cookie。
 * 2.每次请求比对时间，默认20分钟，如果超时，则进行数据库查询，比对是否选择了记住。
 * 3.如果选择记住，则自动完成登陆。如果没有，则认为掉线。
 * @author Liu jun
 */
public class CookieUtil {

	private static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

	//定义常用cookie map中的key
	/**
	 * 加密的用户信息保存在cookie中的键<br>
	 * umm = user's info map
	 */
	public static final String COOKIE_ACCOUNT_NAME_KEY = "__u_id";

	/**
	 * 加密的用户信息保存在cookie中的键<br>
	 * umm = user's info map
	 */
	public static final String COOKIE_ACCOUNT_PASS_KEY = "__u_ps";

	/**
	 * 上次访问菜单键
	 */
	public static final String COOKIE_LAST_MENU_KEY = "__lastMenu";

	/**
	 * 根据名称获取request中的cookie
	 * @param request 请求体
	 * @param cookieName cookie名称
	 * @return cookie
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		if(request == null || request.getCookies() == null) {
			return null;
		}
		for(Cookie cookie : request.getCookies()) {
			if(cookieName.equals(cookie.getName())) {
				return cookie;
			}
		}
		return null;
	}

	/**
	 * 设置新cookie，加入响应体，默认 HttpOnly 过期时间为0
	 * @param response	响应体
	 * @param cookieName	cookie名称
	 * @param cookieValue	cookie值
	 * @param expireTime	可选，过期时间，不传关闭浏览器过期
	 * @return 新增的cookie
	 */
	public static Cookie setCookie(HttpServletResponse response, String cookieName, String cookieValue, Integer... expireTime) {
		if(response == null || StringUtils.isBlank(cookieName)) {
			return null;
		}
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath("/");	//当前工程下访问
		Integer expire = null;
		if(expireTime.length != 0) {	//设置过期时间
			expire = expireTime[0];
			cookie.setMaxAge(expire);
		}
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		logger.warn("write cookie: " + cookieName + " - " + cookieValue + " - " + expire);
		return cookie;
	}

	/**
	 * 删除cookie
	 * @param request	请求体
	 * @param response	响应体
	 * @param cookieName	cookie名称
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		Cookie cookie = getCookie(request, cookieName);
		if(cookie == null) {	//该名称cookie为新cookie
			return;
		}
		//删除cookie，将其值设为null, 并使其立即过期
		setCookie(response, cookieName, null, 0);
	}
}