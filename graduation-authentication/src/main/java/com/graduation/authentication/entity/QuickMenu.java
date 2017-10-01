package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * 权限-账号快速菜单表-实体
 * @author Liu Jun at 2016-9-22 21:30:45
 * @since v1.0.0
 */
@Entity
@Table(name = "T_AUTH_QUICK_MODULE")
public class QuickMenu extends BaseEntity {
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

	@NotFound(action = NotFoundAction.EXCEPTION)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
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

	@NotFound(action = NotFoundAction.EXCEPTION)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "MENU_ID", referencedColumnName = "ID", insertable = false, updatable = false)
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