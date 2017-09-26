package com.graduation.core.base.exception;


import com.graduation.core.base.dto.JsonResult;

/**
 * 系统级异常封装<br>
 * 引入了JsonResult错误提示对象，结合框架支持系统级异常提示
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 系统封装操作反馈对象
	 */
	private JsonResult result;

	/**
	 * @see Exception#Exception(String)
	 */
	public BusinessException(String message) {
		super(message);
		this.result = new JsonResult(false, message);
	}

	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		this.result = new JsonResult(false, message);
	}

	/**
	 * 带自定义JsonResult的异常
	 * @see Exception#Exception(String, Throwable)
	 */
	public BusinessException(Throwable cause, JsonResult result) {
		super(result == null ? "" : result.getMessage(), cause);
		this.result = result == null ? new JsonResult(false, cause.getMessage()) : result;
	}

	public JsonResult getResult() {
		return result;
	}

	public void setResult(JsonResult result) {
		this.result = result;
	}
}