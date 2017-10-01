package com.graduation.authentication.entity;

import com.graduation.core.base.entity.TreeEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * 权限-菜单-实体
 * @author Liu Jun at 2016-8-8 22:37:59
 * @since v1.0.0
 */
@Entity
@Table(name = "T_AUTH_MENU")
public class Menu extends TreeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单资源标识符
     */
    @Length(max = 500, message = "菜单地址长度不能超过200")
    private String uri;

    /**
     * 图标
     */
    @Length(max = 100, message = "图标长度不能超过100")
    private String icon;

    /**
     * 大图标
     */
    @Length(max = 100, message = "大图标长度不能超过100")
    private String bigIcon;

    /**
     * 描述
     */
    @Length(max = 1000, message = "帮助长度不能超过1000")
    private String description;

    /**
     * 菜单级别
     */
    @Digits(integer = 2, fraction = 0, message = "菜单级别长度不能超过2")
    private Integer level;

    /**
     * 备注
     */
    @Length(max = 1000, message = "备注长度不能超过1000")
    private String remark;

    /**
     * 快捷键
     */
    @Length(max = 100, message = "快捷键长度不能超过100")
    private String shortcut;

    /**
     * 菜单性质：0全部；1前台；2后台
     */
    @Digits(integer = 1, fraction = 0, message = "菜单类型长度不能超过1")
    private Integer isPublic;

    /**
     * 是否可分配
     */
    @NotNull(message = "是否可分配不能为空")
    private Boolean assignable;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID", insertable = false, updatable = false, unique = true)
    public Menu getParent() {
        return (Menu)parent;
    }

    public <E extends TreeEntity> void setParent(E parent) {
        this.parent = parent;
    }

    @Column(name = "URI")
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Column(name = "ICON")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "BIG_ICON")
    public String getBigIcon() {
        return bigIcon;
    }

    public void setBigIcon(String bigIcon) {
        this.bigIcon = bigIcon;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Column(name = "SHORTCUT")
    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    @Column(name = "IS_PUBLIC")
    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    @Column(name = "ASSIGNABLE")
    @Type(type = "yes_no")
    public Boolean getAssignable() {
        return assignable;
    }

    public void setAssignable(Boolean assignable) {
        this.assignable = assignable;
    }
}