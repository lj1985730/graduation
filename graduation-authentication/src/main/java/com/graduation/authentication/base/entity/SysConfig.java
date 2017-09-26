/*
 * SysConfig.java
 * Copyright(C) 2015 大连陆海科技股份有限公司
 * All right reserved.
 */
package com.graduation.authentication.base.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * 基础-系统参数-实体
 * @author Liu Jun
 * @version 2016-7-31 14:14:44
 */
@Entity
@Table(name = "T_SYS_CONFIG")
public class SysConfig extends BaseEntity {

	public static final long serialVersionUID = 1L;

	/**
	 * 配置名
	 **/
	@NotBlank(message = "配置Key不能为空")
	@Length(max = 50, message = "配置Key长度不能超过50")
	private String key;

	/**
	 * 配置值
	 **/
	@Length(max = 2000, message = "配置值长度不能超过2000")
	private String value;

	/**
	 * 可否编辑
	 **/
	@NotBlank(message = "可否编辑不能为空")
	private Boolean editable;

	/**
	 * 是否启用
	 **/
	@NotBlank(message = "是否启用不能为空")
	private Boolean enable;

	/**
	 * 备注
	 **/
	@Length(max = 2000, message = "备注长度不能超过2000")
	private String remark;

	@Column(name = "CONFIG_KEY")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "CONFIG_VALUE", columnDefinition = "varchar(2000)")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "EDITABLE")
	@Type(type = "yes_no")
	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	@Column(name = "ENABLE")
	@Type(type = "yes_no")
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Column(name = "REMARK", columnDefinition = "varchar(2000)")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}