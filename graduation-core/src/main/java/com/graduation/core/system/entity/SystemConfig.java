package com.graduation.core.system.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 通用-系统配置-实体
 * @author Liu Jun
 * @since V1.0.0
 */
@Entity
@Table(name = "T_SYS_CONFIG")
public class SystemConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 配置键
	 */
	@NotBlank(message = "配置键不能为空")
	@Length(max = 200, message = "'配置键'长度不能超过200")
	private String key;

	/**
	 * 配置值
	 */
	@Length(max = 500, message = "'配置值'长度不能超过500")
	private String value;

    /**
     * 是否生效
     */
    @NotNull(message = "'是否生效'不能为空")
    private Boolean enable;

	/**
	 * 是否可在系统界面修改
	 */
    @NotNull(message = "'是否可在系统界面修改'不能为空")
	private Boolean editable;

	/**
	 * 备注
	 */
	@Length(max = 1000, message = "备注长度不能超过1000")
	private String remark;

    @Column(name = "CFG_KEY")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "CFG_VALUE")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "ENABLE")
    @Type(type = "yes_no")
    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Column(name = "EDITABLE")
    @Type(type = "yes_no")
    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}