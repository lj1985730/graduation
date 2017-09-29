package com.graduation.core.base.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

/**
 * A convenience class used to easly reply to an async request. This class is
 * designed to be converted to a JSON object using a suitable serializer library
 * (eg. Google Gson library).
 */
//@JsonSerialize(include = Inclusion.NON_NULL)
public class JsonResult extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	/**
	 * 操作成功标识
	 */
	public static final String KEY_SUCCESS = "success";
	
	/**
	 * 反馈提示信息
	 */
	public static final String KEY_MESSAGE = "message";
	
	/**
	 * 操作反馈数据
	 */
	public static final String KEY_DATA = "data";
	
	/**
	 * 统一的操作反馈提示内容
	 */
	public static final String CREATE_SUCCEED = "增加成功！";
	public static final String CREATE_FAIL = "增加失败！";
	public static final String UPDATE_SUCCEED = "修改成功！";
	public static final String UPDATE_FAIL = "修改失败！";
	public static final String DELETE_SUCCEED = "删除成功！";
	public static final String DELETE_FAIL = "删除失败！";

	/**
	 * 返回成功提示
	 */
	public JsonResult() {
		super();
		this.setSuccess(true);
	}

	/**
	 * 返回成功提示
	 * @param data 返回数据
	 */
	public JsonResult(Object data) {
		super();
		this.setSuccess(true);
		this.setData(data);
	}

	/**
	 * 返回提示
	 * @param success	是否成功
	 * @param message	提示内容
	 */
	public JsonResult(boolean success, String message) {
		super();
		this.setSuccess(success);
		this.setMessage(message);
	}

	/**
	 * 返回提示
	 * @param success	是否成功
	 * @param message	提示内容
	 * @param data 返回数据
	 */
	public JsonResult(Boolean success, String message, Object data) {
		super();
		this.setSuccess(success);
		this.setMessage(message);
		this.setData(data);
	}

	public Boolean getSuccess() {
		return (Boolean) this.get(KEY_SUCCESS);
	}

	public JsonResult setSuccess(Boolean value) {
		this.put(KEY_SUCCESS, value);
		return this;
	}

	public String getMessage() {
		return (String) this.get(KEY_MESSAGE);
	}

	public JsonResult setMessage(String value) {
		this.put(KEY_MESSAGE, value);
		return this;
	}

	public Object getData() {
		return this.get(KEY_DATA);
	}

	public JsonResult setData(Object value) {
		this.put(KEY_DATA, value);
		return this;
	}

	/***
	 * Serialize class, except for null values, into a Json string.
	 * @return String
	 */
	public String toJson() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return new String(mapper.writeValueAsBytes(this), "UTF-8");
	}
}