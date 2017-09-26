package com.graduation.core.base.entity;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * 具有树形结构的实体基类，供树形实体类继承
 * @author 刘俊
 */
@MappedSuperclass
public class TreeEntity extends BaseEntity {

	/**
	 * 父节点主键
	 */
	@Length(max = 36, message = "父节点内容异常")
	protected String parentId;

	/**
	 * 父节点
	 */
	protected TreeEntity parent;

	/**
	 * 名称，树形显示内容
	 */
	@NotBlank(message = "名称不可为空")
	@Length(max = 500, message = "名称长度不可大于500")
	protected String name;

	/**
	 * 排序
	 */
	protected Integer sortNo;

	/**
	 * 是否使用
	 */
	@Length(max = 1, message = "是否使用长度不能超过1")
	protected Boolean enabled;

	@Column(name = "PARENT_ID")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SORT_NO")
	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	@Column(name = "ENABLED")
	@Type(type = "yes_no")
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}