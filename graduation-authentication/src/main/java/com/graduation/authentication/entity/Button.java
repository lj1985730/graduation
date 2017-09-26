package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Digits;

/**
 * 权限-按钮-实体
 * @author Liu Jun
 * @version 2016-7-31 14:14:44
 */
@Entity
@Table(name = "T_AUTH_BUTTON")
public class Button extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单
     */
    private Menu menu;

    /**
     * 菜单ID
     */
    @NotBlank(message = "菜单ID不能为空")
    @Length(max = 36, message = "菜单ID长度不能超过36")
    private String menuId;

    /**
     * 按钮名称
     */
    @NotBlank(message = "按钮名称不能为空")
    @Length(max = 50, message = "按钮名称长度不能超过50")
    private String name;

    /**
     * 按钮样式
     */
    @Length(max = 50, message = "按钮图标长度不能超过50")
    private String icon;

    /**
     * 按钮类型
     */
    @Digits(integer = 1, fraction = 0, message = "按钮类型长度不能超过1")
    private Integer type;

    /**
     * 是否使用
     */
    @NotBlank(message = "是否使用不能为空")
    private Boolean enabled;

    /**
     * 备注
     */
    @Length(max = 2000, message = "备注长度不能超过2000")
    private String remark;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID", insertable = false, updatable = false, referencedColumnName = "ID")
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

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ICON")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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