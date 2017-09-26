package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import com.graduation.authentication.base.entity.SysDict;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 权限-教育经历-实体
 * @author Liu Jun
 * @version 22016-8-14 21:47:29
 */
@Entity
@Table(name = "T_AUTH_EDUCATION_EXPERIENCE")
public class EducationExperience extends BaseEntity {

	public static final long serialVersionUID = 1L;

	/**
	 * 人员ID
	 **/
	@Length(max = 36, message = "人员ID长度不能超过36")
	private String personId;

	/**
	 * 教育机构
	 **/
	@Length(max = 500, message = "教育机构长度不能超过500")
	private String institution;

	/**
	 * 开始时间
	 **/
	@NotNull(message = "开始时间不可为空")
	private LocalDate startDate;

	/**
	 * 结束时间
	 **/
	@NotNull(message = "结束时间不可为空")
	private LocalDate endDate;

	/**
	 * 学历（字典码表）
	 **/
	private String backgroundId;

	/**
	 * 学历（字典码表）
	 **/
	private SysDict background;

	/**
	 * 获取人员ID
	 **/
	@Column(name = "PERSON_ID", columnDefinition="char(36)")
	public String getPersonId() {
		return personId;
	}

	/**
	 * 设置人员ID
	 **/
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
	 * 获取教育机构
	 */
	@Column(name = "INSTITUTION")
	public String getInstitution() {
		return institution;
	}

	/**
	 * 设置教育机构
	 **/
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/**
	 * 获取开始时间
	 **/
	@Column(name = "START_DATE")
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * 设置开始时间
	 **/
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * 获取结束时间
	 **/
	@Column(name = "END_DATE")
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * 设置结束时间
	 **/
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取学历ID
	 **/
	@Column(name = "BACKGROUND")
	public String getBackgroundId() {
		return backgroundId;
	}

	/**
	 * 设置学历ID
	 **/
	public void setBackgroundId(String backgroundId) {
		this.backgroundId = backgroundId;
	}

	/**
	 * 获取学历
	 **/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BACKGROUND", insertable = false, updatable = false, referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public SysDict getBackground() {
		return background;
	}

	/**
	 * 设置学历
	 **/
	public void setBackground(SysDict background) {
		this.background = background;
	}
}