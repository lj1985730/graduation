package com.graduation.authentication.entity;

import com.graduation.core.base.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;

/**
 * 权限-部门-实体
 * @author Liu Jun
 * @since v1.0.0
 */
@Entity
@Table(name = "T_AUTH_DEPARTMENT")
public class Department extends TreeEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 英文名称
	 */
	@Length(max = 100, message = "英文名称长度不能超过100")
	private String enName;

	/**
	 * 部门编码
	 */
	@Length(max = 100, message = "部门编码长度不能超过100")
	private String code;

	/**
	 * 部门简称
	 */
	@Length(max = 100, message = "部门简称长度不能超过100")
	private String shortName;

	/**
	 * 部门类型
	 */
	@Length(max = 200, message = "部门类型长度不能超过200")
	private String category;

	/**
	 * 联系人
	 */
	@Length(max = 100, message = "联系人长度不能超过100")
	private String linkman;

	/**
	 * 传真
	 */
	@Length(max = 100, message = "传真长度不能超过100")
	private String fax;

	/**
	 * 电话
	 */
	@Length(max = 100, message = "电话长度不能超过100")
	private String phoneNumber;

	/**
	 * 电子邮件
	 */
	@Length(max = 100, message = "电子邮件长度不能超过100")
	private String email;

	/**
	 * 部门等级
	 */
	@Digits(integer = 2, fraction = 0, message = "部门等级长度不能超过2")
	private Integer level;

	/**
	 * 备注
	 */
	@Length(max = 1000, message = "备注长度不能超过1000")
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

    @Column(name = "CATEGORY")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


	@Column(name = "LINKMAN")
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "PHONE_NUMBER")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}