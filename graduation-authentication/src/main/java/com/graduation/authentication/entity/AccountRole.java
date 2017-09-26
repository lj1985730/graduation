package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * 权限-账户角色关系-实体
 * @author Liu Jun
 * @version 2016-7-31 14:14:44
 */
@Entity
@Table(name = "T_AUTH_ACCOUNT_ROLE")
public class AccountRole extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 登录用户
	 */
	private Account account;

	/**
	 * 登录用户ID
	 **/
	@NotBlank(message = "用户ID不能为空")
	@Length(max = 36, message = "用户ID长度不能超过36")
	private String accountId;

	/**
	 * 角色
	 */
	private Role role;

	/**
	 * 角色ID
	 **/
	@NotBlank(message = "角色ID不能为空")
	@Length(max = 36, message = "角色ID长度不能超过36")
	private String roleId;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Column(name = "ACCOUNT_ID")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}