package com.graduation.core.base.controller;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 控制器的抽象父类，用于处理通用功能。 <br>
 * 目前处理了页面模板的转换。
 * @author graduation
 */
public abstract class BaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 页面相对路径
	 */
	private static final String DEFAULT_TEMPLATE = "/jsp";

	/**
	 * 返回页面视图
	 * @param page {@link #getTemplatePath(String)}
	 * @return 对象视图字符串
	 */
	protected String pageView(String page) {
		return getTemplatePath(page);
	}

	/**
	 * 返回页面和对象视图
	 * @see #pageView(String)
	 * @param page {@link #getTemplatePath(String)}
	 * @param modelName	对象名称，前台需要用modelName.属性名获取数据
	 * @param modelObject	对象实例
	 * @return 对象视图
	 */
	protected ModelAndView pageView(String page, String modelName, Object modelObject) {
		String template = getTemplatePath(page);
		logger.debug("调用模板路径：" + template);
		return new ModelAndView(template, modelName, modelObject);
	}


	/**
	 * 返回页面和对象视图
	 * @see #pageView(String)
	 * @param page {@link #getTemplatePath(String)}
	 * @param dataMap	数据集合
	 * @return 对象视图
	 */
	protected ModelAndView pageView(String page, Map<String, ?> dataMap) {
		String template = getTemplatePath(page);
		return new ModelAndView(template, dataMap);
	}
	
	/**
	 * 获取模板视图统一路径
	 * @param page 需要提供第一个/用来表示模板目录的根目录,例如：<br>
	 *            /baseinfo/hello表示路径：WEB-INF/templates/jsp/baseinfo/hello,
	 *            其中jsp需要根据 用户的自定义模板来确定。
	 * @return 模板视图路径
	 */
	private String getTemplatePath(String page) {
		return DEFAULT_TEMPLATE + page; // 模板名(根据用户不同设定可变)
	}
	
	/**
	 * 重定向页面
	 * @param relativePath 视图路径
	 * @return 重定向
	 */
	protected String redirectPageView(String relativePath) {
		return "redirect:" + relativePath;
	}

	/**
	 * 实现统一异常处理，数值格式化异常
	 * @param exception 捕获到的异常
	 * @param request 请求体
	 * @return 根据不同请求类型进行错误反馈或错误页跳转
	 */
	@ExceptionHandler(value = NumberFormatException.class)
	@ResponseBody
	public Object numberFormatExceptionHandler(NumberFormatException exception, HttpServletRequest request) {
		logger.error("BaseController numberFormatExceptionHandler:", exception);
		String contentType = request.getContentType();
		if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
			request.setAttribute("message", exception.getMessage());
			return new JsonResult(false, "数据处理出现错误：无效的数值格式！");
		} else {	//否则重定向到错误页面
			return pageView("/error", "message", exception.getMessage());
		}
	}

	/**
	 * 实现统一异常处理，SQL异常
	 * @param exception 捕获到的异常
	 * @param request 请求体
	 * @return 根据不同请求类型进行错误反馈或错误页跳转
	 */
	@ExceptionHandler(value = SQLException.class)
	@ResponseBody
	public Object sqlExceptionHandler(SQLException exception, HttpServletRequest request) {
		logger.error("BaseController sqlExceptionHandler:", exception);
		String contentType = request.getContentType();
		if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
			request.setAttribute("message", exception.getMessage());
			return new JsonResult(false, "数据库访问错误！");
		} else {	//否则重定向到错误页面
			return pageView("/error", "message", exception.getMessage());
		}
	}

	/**
	 * 实现统一异常处理，NullPointer异常
	 * @param exception 捕获到的异常
	 * @param request 请求体
	 * @return 根据不同请求类型进行错误反馈或错误页跳转
	 */
	@ExceptionHandler(value = NullPointerException.class)
	@ResponseBody
	public Object nullPointerExceptionHandler(NullPointerException exception, HttpServletRequest request) {
		logger.error("BaseController nullPointerExceptionHandler:", exception);
		String contentType = request.getContentType();
		if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
			request.setAttribute("message", exception.getMessage());
			return new JsonResult(false, "数据处理出现错误：空指针！");
		} else {	//否则重定向到错误页面
			return pageView("/error", "message", exception.getMessage());
		}
	}
	
	/**
	 * 实现统一异常处理，数据约束冲突异常,约束由entity中注解确定
	 * @param exception 捕获异常
	 */
	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseBody
	public Object constraintViolationExceptionHandler(ConstraintViolationException exception, HttpServletRequest request) {
		logger.error("BaseController constraintViolationExceptionHandler 捕获系统异常：", exception);
		System.out.println(exception.getMessage());
		Set<ConstraintViolation<?>> messages = exception.getConstraintViolations();
		final StringBuilder message = new StringBuilder("编辑数据异常：");
		if(messages != null) {
			messages.forEach(constraintViolation -> message.append(constraintViolation.getMessage()).append("，"));
		}
		String messageStr = StringUtils.removeEnd(message.toString(), "，");
		
		String contentType = request.getContentType();
		if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
			request.setAttribute("message", messageStr);
			return new JsonResult(false, messageStr);
		} else {	//否则重定向到错误页面
			return pageView("/error", "message", message);
		}
	}

	/**
	 * 实现统一异常处理，系统自定义异常
	 * @param exception 捕获到的异常
	 * @param request 请求体
	 * @return 根据不同请求类型进行错误反馈或错误页跳转
	 */
	@ExceptionHandler(value = BusinessException.class)
	@ResponseBody
	public Object businessExceptionHandler(BusinessException exception, HttpServletRequest request) {
		logger.error("BaseController BusinessException Handler:", exception);
		String contentType = request.getContentType();
		if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
			request.setAttribute("message", exception.getMessage());
			return exception.getResult();
		} else {	//否则重定向到错误页面
			return pageView("/error", "message", exception.getMessage());
		}
	}

	/**
	 * 实现集中异常处理
	 * @param exception 捕获到的异常
	 * @param request 请求体
	 * @return 根据不同请求类型进行错误反馈或错误页跳转
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object exceptionHandler(Exception exception, HttpServletRequest request) {
		logger.error("BaseController exceptionHandler 捕获系统异常：", exception);
		String contentType = request.getContentType();
		if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
			request.setAttribute("message", exception.getMessage());
			return new JsonResult(false, exception.getMessage());
		} else {	//否则重定向到错误页面
			return pageView("/error", "message", exception.getMessage());
		}
	}
}