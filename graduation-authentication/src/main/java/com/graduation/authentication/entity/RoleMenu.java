package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 权限-角色菜单关系-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @since v1.0.0
 */
@Entity
@Table(name = "T_AUTH_ROLE_MENU")
public class RoleMenu extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@NotBlank(message = "角色ID不能为空")
	@Length(max = 36, message = "角色ID长度不能超过36")
	private String roleId;

	/**
	 * 菜单
	 */
	@NotNull(message = "菜单不能为空")
	private Menu menu;

	/**
	 * 菜单ID
	 */
	@NotBlank(message = "菜单ID不能为空")
	@Length(max = 36, message = "菜单ID长度不能超过36")
	private String menuId;

	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@NotFound(action = NotFoundAction.EXCEPTION)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
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