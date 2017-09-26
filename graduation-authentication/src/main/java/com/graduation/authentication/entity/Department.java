package com.graduation.authentication.entity;

import com.graduation.core.base.entity.TreeEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;

/**
 * 权限-部门-实体
 * @author Liu Jun
 * @version 2016-7-31 14:14:44
 */
@Entity
@Table(name = "T_AUTH_DEPARTMENT")
public class Department extends TreeEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门英文
	 */
	@Length(max = 500, message = "部门英文长度不能超过500")
	private String enName;

	/**
	 * 部门编码
	 */
	@Length(max = 500, message = "部门编码长度不能超过500")
	private String code;

	/**
	 * 部门简称
	 */
	@Length(max = 500, message = "部门简称长度不能超过500")
	private String shortName;

	/**
	 * 部门类型
	 */
	@Digits(integer = 2, fraction = 0, message = "部门类型长度不能超过2")
	private Integer type;

	/**
	 * 联系人
	 */
	@Length(max = 500, message = "联系人长度不能超过500")
	private String linkMan;

	/**
	 * 传真
	 */
	@Length(max = 500, message = "传真长度不能超过500")
	private String fax;

	/**
	 * 电话
	 */
	@Length(max = 500, message = "电话长度不能超过500")
	private String telephone;

	/**
	 * 电子邮件
	 */
	@Length(max = 500, message = "电子邮件长度不能超过500")
	private String email;

	/**
	 * 部门等级
	 */
	@Digits(integer = 2, fraction = 0, message = "部门等级长度不能超过2")
	private Integer level;

	/**
	 * 是否使用
	 */
	private Boolean enabled;

	/**
	 * 备注
	 */
	@Length(max = 2000, message = "备注长度不能超过2000")
	private String remark;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID", insertable = false, updatable = false, unique = true)
	public Department getParent() {
		return (Department)parent;
	}

	public <E extends TreeEntity> void setParent(E parent) {
		this.parent = parent;
	}

	@Column(name = "EN_NAME")
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "SHORT_NAME")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "TYPE")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "LINK_MAN")
	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "TELEPHONE")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "LEVEL")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "ENABLED")
	@Type(type = "yes_no")
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}