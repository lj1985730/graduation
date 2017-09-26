package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * 权限-用户便捷菜单-实体
 * @author Liu Jun
 * @version 2016-9-22 21:30:45
 */
@Entity
@Table(name = "T_AUTH_MODULE")
public class Module extends BaseEntity {
	public static final long serialVersionUID = 1L;

	/**
	 * 关联账户（查询）
	 */
	private Account account;
	
	/**
	 * 关联账户Id（编辑）
	 */
	@NotBlank(message = "关联账户不能为空！")
	@Length(max = 36, message = "关联账户Id长度不能超过36!")
	private String accountId;

	/**
	 * 关联菜单（查询）
	 */
	private Menu menu;

	/**
	 * 关联菜单Id（编辑）
	 */
	@NotBlank(message = "关联菜单不能为空！")
	@Length(max = 36, message = "关联菜单Id长度不能超过36!")
	private String menuId;

	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.EXCEPTION)
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
	
	@ManyToOne
	@JoinColumn(name = "MENU_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.EXCEPTION)
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Column(name = "MENU_ID")
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}