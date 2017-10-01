package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 权限-角色按钮关系-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @since v1.0.0
 */
@Entity
@Table(name = "T_AUTH_ROLE_BUTTON")
public class RoleButton extends BaseEntity {

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
	private Button button;

    /**
     * 按钮ID
     */
	@NotBlank(message = "按钮ID不能为空")
    @Length(max = 36, message = "按钮ID长度不能超过36")
    private String buttonId;

    @Column(name = "ROLE_ID")
    public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@NotFound(action = NotFoundAction.EXCEPTION)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "BUTTON_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	@Column(name = "BUTTON_ID")
	public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}
}