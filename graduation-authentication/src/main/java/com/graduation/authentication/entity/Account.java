package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 权限-账户-实体
 * @author Liu Jun
 * @version 2016-7-31 14:14:44
 */
@Entity
@Table(name = "T_AUTH_ACCOUNT")
public class Account extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	@Length(max = 50, message = "用户名长度不能超过50")
	private String name;

	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	@Length(max = 50, message = "密码长度不能超过50")
	private String password;

	/**
	 * 人员
	 */
	private Person person;

	/**
	 * 人员ID
	 */
	@Length(max = 36, message = "人员ID长度不能超过36")
	private String personId;

	/**
	 * 是否超管
	 */
	@NotNull(message = "是否超管不可为空")
	private Boolean superAccount;

	/**
	 * 用户等级
	 */
	@Digits(integer = 9, fraction = 0, message = "用户等级长度不能超过(9,0)")
	private Integer level;

	/**
	 * 最后一次登录时间
	 */
	private LocalDateTime lastLogon;

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

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, referencedColumnName = "ID", unique = true)
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Column(name = "PERSON_ID")
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column(name = "SUPER_ACCOUNT")
	@Type(type = "yes_no")
	public Boolean getSuperAccount() {
		return superAccount;
	}

	public void setSuperAccount(Boolean superAccount) {
		this.superAccount = superAccount;
	}

	@Column(name = "LEVEL")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "LAST_LOGON")
	public LocalDateTime getLastLogon() {
		return lastLogon;
	}

	public void setLastLogon(LocalDateTime lastLogon) {
		this.lastLogon = lastLogon;
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