package com.graduation.core.base.security;

/**
 * 系统加密器
 * @author liujun
 */
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

/**
 *
 */
public class Encryptor {

	/**
	 * 密码加密盐值
	 */
	private static final String PASS_SALT = "graduation";

	/**
	 * TOKEN加密盐值<br>
	 */
	private static final String TOKEN_SALT = "liuj_his";

	/**
	 *  密码加密器
	 */
    private static MessageDigestPasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("MD5", true);

	/**
	 * 密码加密<br>
	 * 1.MD5加密
	 * 2.盐值为PASS_SALT的base64加密
	 * @param password	密码明文
	 * @return	密码密文
	 */
	public static String encryptPassword(String password) {
		return passwordEncoder.encodePassword(password, PASS_SALT);
	}
	
	/**
	 * token加密<br>
	 * 1.MD5加密
	 * 2.盐值为TOKEN_SALT的base64加密
	 * @param token 	token明文
	 * @return	密码密文
	 */
	public static String encryptToken(String token) {
		return passwordEncoder.encodePassword(token, TOKEN_SALT);
	}
	
	/**
	 * 密码校验<br>
	 * @param encPass	密码密文
	 * @param rawPass	待校验密码明文
	 * @return	校验结果
	 */
	public static Boolean validPassword(String encPass, String rawPass) {
		return passwordEncoder.isPasswordValid(encPass, rawPass, PASS_SALT);
	}

	/**
	 * token校验<br>
	 * @param encToken	token密文
	 * @param rawToken	待校验token明文
	 * @return	校验结果
	 */
	public static Boolean validToken(String encToken, String rawToken) {
		return passwordEncoder.isPasswordValid(encToken, rawToken, TOKEN_SALT);
	}
}
