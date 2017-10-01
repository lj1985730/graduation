package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 权限-角色-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @since v1.0.0
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
	 * 是否系统角色
	 */
	@NotNull(message = "是否系统角色不可为空")
	private Boolean isSystem;

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

	@Column(name = "ENABLED")
	@Type(type = "yes_no")
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "IS_SYSTEM")
	@Type(type = "yes_no")
	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}