package com.graduation.authentication.entity;

import com.graduation.core.base.entity.TreeEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * 权限-菜单-实体
 * @author Liu Jun
 * @version 2016-8-8 22:37:59
 */
@Entity
@Table(name = "T_AUTH_MENU")
public class Menu extends TreeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 系统类型
     */
    public enum SystemType {

        ALL(0), //全部
        BASE_AUTHORITY(1),  //基础权限
        PATIENT_DOCTOR_STATION(2),  //门诊医生站
        WARD_DOCTOR_STATION(3),    //病房医生站
        WARD_NURSE_STATION(4);    //病房护士站

        private int value;

        SystemType(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.value);
        }
    }

    /**
     * 菜单地址
     */
    @Length(max = 200, message = "菜单地址长度不能超过200")
    private String url;

    /**
     * 图标
     */
    @Length(max = 50, message = "图标长度不能超过50")
    private String icon;

    /**
     * 大图标
     */
    @Length(max = 50, message = "大图标长度不能超过50")
    private String bigIcon;

    /**
     * 帮助
     */
    @Length(max = 1000, message = "帮助长度不能超过1000")
    private String help;

    /**
     * 业务树
     */
    @Length(max = 200, message = "业务树长度不能超过200")
    private String business;

    /**
     * 菜单级别
     */
    @Length(max = 50, message = "菜单级别长度不能超过50")
    private Integer level;

    /**
     * 备注
     */
    @Length(max = 2000, message = "备注长度不能超过2000")
    private String remark;

    /**
     * 快捷键
     */
    @Length(max = 100, message = "快捷键长度不能超过100")
    private String shortcut;

    /**
     * 菜单类型
     */
    @Digits(integer = 1, fraction = 0, message = "菜单类型长度不能超过1")
    private Integer type;

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

    /**
     * 系统类型
     */
    @NotNull(message = "所属系统不可为空")
    @Enumerated(value = EnumType.STRING)
    private SystemType systemType;


    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID", insertable = false, updatable = false, unique = true)
    public Menu getParent() {
        return (Menu)parent;
    }

    public <E extends TreeEntity> void setParent(E parent) {
        this.parent = parent;
    }

    @Column(name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Column(name = "HELP")
    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    @Column(name = "BUSINESS")
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
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

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    @Column(name = "SYSTEM_TYPE")
    public SystemType getSystemType() {
        return systemType;
    }

    public void setSystemType(SystemType systemType) {
        this.systemType = systemType;
    }
}