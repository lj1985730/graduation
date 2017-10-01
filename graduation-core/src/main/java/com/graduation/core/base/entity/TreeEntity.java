package com.graduation.core.base.entity;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

/**
 * 具有树形结构的实体基类，供树形实体类继承
 * @author 刘俊
 */
@MappedSuperclass
public class TreeEntity extends BaseEntity {

    /**
     * 名称，树形显示内容
     */
    @NotBlank(message = "名称不可为空")
    @Length(max = 200, message = "名称长度不可大于200")
    private String name;

	/**
	 * 父节点主键
	 */
	@Length(max = 36, message = "父节点内容异常")
	private String parentId;

	/**
	 * 父节点
	 */
	protected TreeEntity parent;

	/**
	 * 排序
	 */
	@Digits(integer = 9, fraction = 0, message = "排序长度不能超过9")
	private Integer sort;

	/**
	 * 是否使用
	 */
	@NotBlank(message = "是否使用不可为空")
	private Boolean enable;


    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Column(name = "PARENT_ID")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "SORT_NUMBER")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "ENABLE")
	@Type(type = "yes_no")
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
}