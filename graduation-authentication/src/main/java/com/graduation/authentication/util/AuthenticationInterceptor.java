package com.graduation.authentication.util;

import com.graduation.core.base.dto.JsonResult;
import com.graduation.web.util.CookieUtils;
import com.graduation.web.util.HttpErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;


/**
 * 身份拦截器，用于实现身份，权限的校验。
 * @author Liu Jun
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return pcPreHandle(request, response);
	}

	/**
	 * PC客户端处理
	 * @return 是否通过
	 */
	private boolean pcPreHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String requestUri = request.getRequestURI();	//检查菜单权限
		if(requestUri.endsWith("/homeView")) {	//检验用户是否有请求页面的权限
			String[] authMenuUrlArr = AuthenticationUtils.getMenuIdArr();

			if(authMenuUrlArr == null) {
                respondError(HttpErrorCode.NO_AUTHORIZATION, request, response);
                return false;
            }

			List<String> authMenuUrlList = Arrays.asList(authMenuUrlArr);

			if(authMenuUrlList.isEmpty()) {
				respondError(HttpErrorCode.NO_AUTHORIZATION, request, response);
				return false;
			}

			if(!authMenuUrlList.contains(StringUtils.removeStart(requestUri, request.getContextPath() + "/"))) {
				respondError(HttpErrorCode.NO_AUTHORIZATION, request, response);
				return false;
			}
		}

		setConstants(response);

		return true;
	}

	/**
	 * 设置常量
	 * @param response 应答体
     */
	private void setConstants(HttpServletResponse response) {
		CookieUtils.setUnsafeCookie(response, AuthenticationUtils.LoginInfo.ACCOUNT_ID.toString(), AuthenticationUtils.getAccountId());		//账户Id
		CookieUtils.setUnsafeCookie(response, AuthenticationUtils.LoginInfo.PERSON_NAME.toString(), AuthenticationUtils.getPersonId());		//人员Id
		try {
			if(StringUtils.isNotBlank( AuthenticationUtils.getDepartmentName() )) {
				CookieUtils.setUnsafeCookie(response, AuthenticationUtils.LoginInfo.DEPARTMENT_NAME.toString(), URLEncoder.encode(AuthenticationUtils.getDepartmentName(), "UTF-8"));	//企业名称
			} else {
				CookieUtils.setUnsafeCookie(response, AuthenticationUtils.LoginInfo.DEPARTMENT_NAME.toString(), "");
			}
			if(StringUtils.isNotBlank( AuthenticationUtils.getAccountName() )) {
				CookieUtils.setUnsafeCookie(response, AuthenticationUtils.LoginInfo.ACCOUNT_NAME.toString(), URLEncoder.encode(AuthenticationUtils.getAccountName(), "UTF-8"));	//账户名称
			}
			if(StringUtils.isNotBlank( AuthenticationUtils.getPersonName() )) {
				CookieUtils.setUnsafeCookie(response, AuthenticationUtils.LoginInfo.PERSON_NAME.toString(), URLEncoder.encode(AuthenticationUtils.getPersonName(), "UTF-8"));	//人员名称
			} else {
				CookieUtils.setUnsafeCookie(response, AuthenticationUtils.LoginInfo.PERSON_NAME.toString(), "");
			}

		} catch (UnsupportedEncodingException e) {
			logger.error("加载用户出错，编码错误！", e);
		}
	}

	/**
	 * 错误响应
	 * @param errorCode	错误代码枚举
	 * @param request	请求
	 * @param response	响应
	 */
	private void respondError(HttpErrorCode errorCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String contentType = request.getContentType();
		if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) { // 如果是json，则返回json类型的数据。
			response.setContentType("application/json;charset=UTF-8");
			JsonResult jsonResult = new JsonResult(false, errorCode.getMessage());
			jsonResult.put("error_code", errorCode);
			response.getWriter().write(jsonResult.toJson());
			response.getWriter().flush();
			logger.error(errorCode.getMessage());
		} else { // 如果是页面，则直接跳转。
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}