package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 权限-角色-实体
 * @author Liu Jun
 * @version 2016-7-31 14:14:44
 */
@Entity
@Table(name = "T_AUTH_ROLE")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色名称
	 */
	@NotBlank(message = "角色名称不能为空")
	@Length(max = 100, message = "角色名称长度不能超过100")
	private String name;

	/**
	 * 角色编码
	 */
	@Length(max = 100, message = "角色编码长度不能超过100")
	private String code;

	/**
	 * 是否使用
	 */
	@NotNull(message = "是否使用不可为空")
	private Boolean enabled;

	/**
	 * 备注
	 */
	@Length(max = 2000, message = "备注长度不能超过2000")
	private String remark;

	/**
	 * 是否系统角色
	 */
	@NotNull(message = "是否系统角色不可为空")
	private Boolean isSystem;

	/**
	 * 部门
	 */
	private Department department;

	/**
	 * 部门ID
	 */
	@Length(max = 36, message = "部门ID长度不能超过36")
	private String departmentId;

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

	@Column(name = "IS_SYSTEM")
	@Type(type = "yes_no")
	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Column(name = "DEPARTMENT_ID")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
}