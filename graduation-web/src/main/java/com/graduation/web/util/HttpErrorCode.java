package com.graduation.web.util;

/**
 * HTTP响应错误码
 * @author Liu Jun
 * @version 2016-8-5 14:58:28
 */
public enum HttpErrorCode {

	/**
	 * 超时
	 */
	EXPIRE("0001", "抱歉，由于您长时间未操作，连接超时，请重新登陆！"),
	
	/**
	 * 请求无token
	 */
	NO_TOKEN("0002", "不安全的访问请求，请重新登录！"),
	
	/**
	 * token不匹配
	 */
	TOKEN_NOT_MATCH("0003", "抱歉，该账户已在其他位置登录！"),
	
	/**
	 * 无用户信息
	 */
	NO_USER_INFO("0004", "未找到登录信息，请重新登录！"),
	
	/**
	 * 重复提交
	 */
	REQUEST_REPEAT("0005", "数据提交中，请耐心等待！"),
	
	/**
	 * 重复提交
	 */
	NO_AUTHORIZATION("0006", "抱歉，您没有改访问权限！"),
	
	/**
	 * 请求无session,用于无redis的环境
	 */
	NO_SESSION("0007", "会话已失效，请重新登录！");
	
	private String code;	//错误编码
	private String message;	//错误提示信息
	
	private HttpErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return this.code;
	}
	
	/**
	 * 获取错误提示信息
	 * @return 错误提示信息
	 */
	public String getMessage() {
		return this.message;
	}
}
