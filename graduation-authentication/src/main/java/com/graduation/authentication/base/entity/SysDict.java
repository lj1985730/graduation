package com.graduation.authentication.base.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 系统-系统字典-实体
 * @author Liu Jun
 * @version 2016-8-14 21:53:49
 */
@Entity
@Table(name = "T_SYS_DICT")
public class SysDict extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 字典编号
	 */
	@NotBlank(message = "字典编号不能为空")
	@Length(max = 50, message = "字典编号长度不能超过50")
	private String code;

	/**
	 * 是否默认
	 */
	@NotNull(message = "是否默认不可为空")
	private Boolean isDefault;

	/**
	 * 字典名称
	 */
	@NotBlank(message = "字典名称不能为空")
	@Length(max = 50, message = "字典名称长度不能超过50")
	private String name;

	/**
	 * 排序
	 */
	@Length(max = 20, message = "排序长度不能超过20")
	private String sortNo;

	/**
	 * 字典分类
	 */
	@NotBlank(message = "字典分类不能为空")
	@Length(max = 50, message = "字典分类长度不能超过50")
	private String category;

	/**
	 * 备注
	 */
	@Length(max = 500, message = "备注长度不能超过500")
	private String remark;

	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "IS_DEFAULT")
	@Type(type = "yes_no")
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SORT_NO")
	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	@Column(name = "CATEGORY")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}