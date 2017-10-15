package com.graduation.bus.infrastructure.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 公交管理-站点-实体
 * @author Liu Jun at 2017-10-13 22:23:11
 * @since v1.0.0
 */
@Entity
@Table(name = "T_BUS_STATION")
public class Station extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@NotBlank(message = "名称不能为空")
	@Length(max = 100, message = "名称长度不能超过100")
	private String name;

	/**
	 * 编号
	 */
	@Length(max = 100, message = "编号长度不能超过100")
	private String code;

	/**
	 * 地址
	 */
    @Length(max = 200, message = "编号长度不能超过200")
	private String location;

	/**
	 * 备注
	 */
    @Length(max = 1000, message = "备注长度不能超过1000")
	private String remark;

	/**
	 * 是否启用
	 */
	@NotNull(message = "是否启用不可为空")
	private Boolean enabled;

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "LOCATION")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "ENABLED")
	@Type(type = "yes_no")
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}