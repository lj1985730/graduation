package com.graduation.core.system.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 通用-系统字典实体
 * @author Liu Jun
 * @since V1.0.0
 */
@Entity
@Table(name = "T_SYS_DICT")
public class SystemDict extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@NotBlank(message = "名称不能为空")
	@Length(max = 200, message = "名称长度不能超过200")
	private String name;

	/**
	 * 编号
	 */
	@NotBlank(message = "编号不能为空")
	@Length(max = 200, message = "编号长度不能超过200")
	private String code;

	/**
	 * 字典分类
	 */
	@NotBlank(message = "字典分类不能为空")
	@Length(max = 200, message = "字典分类长度不能超过200")
	private String category;

	/**
	 * 排序
	 */
	@NotNull(message = "排序不能为空")
	@Digits(integer = 9, fraction = 0, message = "排序长度不能超过(9,0)")
	private Integer sort;

	/**
	 * 备注
	 */
	@Length(max = 1000, message = "备注长度不能超过1000")
	private String remark;

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "CATEGORY")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "SORT_NUMBER")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}