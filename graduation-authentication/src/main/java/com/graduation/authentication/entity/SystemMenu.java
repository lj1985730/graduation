package com.graduation.authentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * 权限-系统菜单关系-实体
 * @author Liu Jun at 2017-10-01 23:18:51
 * @since v1.0.0
 */
@Entity
@Table(name = "T_AUTH_SYSTEM_MENU")
public class SystemMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private System system;

    /**
     * 系统ID
     **/
    @NotBlank(message = "系统ID不能为空")
    @Length(max = 36, message = "系统ID长度不能超过36")
    private String systemId;

    private Menu menu;

    /**
     * 菜单ID
     **/
    @NotBlank(message = "菜单ID不能为空")
    @Length(max = 36, message = "菜单ID长度不能超过36")
    private String menuId;

    @NotFound(action = NotFoundAction.EXCEPTION)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "SYSTEM_ID", insertable = false, updatable = false, referencedColumnName = "ID")
    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    @Column(name = "SYSTEM_ID")
    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @NotFound(action = NotFoundAction.EXCEPTION)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "MENU_ID", insertable = false, updatable = false, referencedColumnName = "ID")
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu sysMenu) {
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