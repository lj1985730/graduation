package com.graduation.core.base.util;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.graduation.core.base.dao.RedisDao;
import com.graduation.core.base.security.Encryptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Token工具集，负责对系统Token进行处理
 * 身份校验机制：每个token只对单次session有效
 * 1.登陆后获得验证通过，形成token。
 * 2.每次请求检索request中的token，并与服务端保存的token值比较
 * 3.请求通过token随response返回客户端
 * @author Liu Jun
 * @version 2016-9-23 16:18:05
 */
public class TokenUtil {

	/**
	 * 保存在request中的token键，安全信息
	 */
	public static final String TOKEN_KEY = "token";
	
	/**
	 * cookie超时时间，单位秒，默认设为7天
	 */
	public static final Integer EXPIRE_SECOND = 7 * 24 * 60 * 60;
	
	/**
	 * 生成未加密token(暂时定为uuid)
	 * @return	未加密token
	 */
	private static String generateToken() {
		return UUID.randomUUID().toString().toUpperCase();
	}
	
	/**
	 * 生成加密token
	 * @return	加密token
	 */
	public static String generateEncodeToken() {
		return encryptToken(generateToken());
	}
	
	/**
	 * 检查token是否存在，token从request中检索
	 * @return true 存在；false 不存在；
	 */
	public static Boolean tokenExist() {
		 return tokenExist(getClientToken());
	}
	
	/**
	 * 检查token是否存在，token从request中检索
	 * @param request 请求体
	 * @return true 存在；false 不存在；
	 */
	public static Boolean tokenExist(HttpServletRequest request) {
		return tokenExist(getClientToken(request));
	}
	
	/**
	 * 检查token是否存在
	 * @param token 登录验证token
	 * @return true 存在；false 不存在；
	 */
	public static Boolean tokenExist(String token) {
		if(StringUtils.isBlank(token)) {
			return false;
		}
		RedisDao dao = SpringContextUtil.getBean("redisDao", RedisDao.class);
		return dao.exist(token);
	}
	
	/**
	 * 在request中搜索token, 查询顺序attribute -> head -> parameter
	 * @return	搜索出的token
	 * 			null，未搜索到
	 */
	public static String getClientToken() {
		if(RequestContextHolder.getRequestAttributes() != null){
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			return getClientToken(request);
		} else {
			return null;
		}
	}

	/**
	 * 在request中搜索token, 查询顺序attribute -> head -> parameter
	 * @param request	搜索对象
	 * @return	搜索出的token
	 * 			null，未搜索到
	 */
	public static String getClientToken(HttpServletRequest request) {
		if(request == null) {
			return null;
		}
		//先在attr中查询
		String token = (String) request.getAttribute(TOKEN_KEY);
		//attr中查询不到，改从head中查询
		if (StringUtils.isEmpty(token)) {
			token = request.getHeader(TOKEN_KEY);
			//head中查询不到，改从param中查询
			if (StringUtils.isEmpty(token)) {
				token = request.getParameter(TOKEN_KEY);
				if (StringUtils.isEmpty(token)) {
					return null;
				}
			}
		}
		return token.replaceAll(" ", "+");
	}

	/**
	 * 向request的Attribute、response的header中增加token信息<br>
	 * 如不传入token，自动生成新的加密token
	 * @param request	待加入token的请求体
	 * @param response	待加入token的应答体
 	 * @param token		（可选）不传参自动生成新的加密token
	 * @return 加密后token
	 */
	public static String setClientToken(HttpServletRequest request, HttpServletResponse response, String... token) {
		String tokenStr;
		if(token != null && token.length > 0) {
			tokenStr = token[0];
		} else {
			tokenStr = generateEncodeToken();
		}
		//存入request和response中,方便跨域使用，系统查询cookie中的用户信息
		//response主要用于ajax回调
		request.setAttribute(TOKEN_KEY, tokenStr);
		response.setHeader(TOKEN_KEY, tokenStr);
		return tokenStr;
	}

	/**
	 * token加密
	 * @param inStr	加密前token
	 * @return 加密之后token值
	 */
	public static String encryptToken(String inStr) {
		return Encryptor.encryptToken(inStr);
	}
}