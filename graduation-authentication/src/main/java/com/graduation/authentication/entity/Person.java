package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 权限-人员-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @since v1.0.0
 */
@Entity
@Table(name = "T_AUTH_PERSON")
public class Person extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 枚举-性别
	 */
	public enum Gender {

		MALE(1), FEMALE(2);

		private int value;

		Gender(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return String.valueOf(this.value);
		}
	}

	/**
	 * 枚举-人员状态 1在职；2离职；3休假；
	 */
	public enum State {

		SERVING(1), LEAVE(2), VACATION(3);

		private int value;

		State(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return String.valueOf(this.value);
		}
	}

	/**
	 * 枚举-人员类型 1医生；2护士；3行政；4其他
	 */
	public enum Category {

		DOCTOR(1), NURSE(2), ADMIN(3), OTHER(4);

		private int value;

		Category(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return String.valueOf(this.value);
		}
	}

	/**
	 * 枚举-证件类型 1身份证；2驾照；3护照；
	 */
	public enum IdType {

		ID_CARD(1), DRIVER_LICENSE(2), PASSPORT(3);

		private int value;

		IdType(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return String.valueOf(this.value);
		}
	}

	/**
	 * 枚举-人员性质 1自有；2借调；3临时；4外派
	 */
	public enum Nature {

		OWN(1), SECONDMENT(2), TEMPORARY(3), ASSIGNMENT(4);

		private int value;

		Nature(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return String.valueOf(this.value);
		}
	}

	/**
	 * 姓名
	 */
	@NotBlank(message = "姓名不能为空")
	@Length(max = 100, message = "姓名长度不能超过100")
	private String name;

	/**
	 * 英文名
	 */
	@Length(max = 100, message = "英文名长度不能超过100")
	private String enName;

	/**
	 * 性别 1：男；2：女
	 */
	@NotNull(message = "性别不可为空")
	@Enumerated(value=EnumType.STRING)
	private Gender gender;

	/**
	 * 证件号
	 */
	@Length(max = 100, message = "证件号长度不能超过100")
	private String idNumber;
	
	/**
	 * 国籍
	 */
	@Length(max = 100, message = "国籍长度不能超过100")
	private String country;
	
	/**
	 * 民族
	 */
	@Length(max = 100, message = "民族长度不能超过100")
	private String nationality;

	/**
	 * 出生地
	 */
	@Length(max = 100, message = "出生地长度不能超过100")
	private String birthPlace;

	/**
	 * 出生日期
	 */
	private LocalDate birthDate;

	/**
	 * 手机号码
	 */
	@Length(max = 100, message = "手机号码长度不能超过100")
	private String mobileNumber;

	/**
	 * 办公号码
	 */
	@Length(max = 100, message = "办公号码长度不能超过100")
	private String officeNumber;

	/**
	 * 邮箱
	 */
	@Length(max = 100, message = "邮箱长度不能超过100")
	private String email;

	/**
	 * 家庭住址
	 */
	@Length(max = 100, message = "家庭住址长度不能超过100")
	private String address;

	/**
	 * 备注
	 */
	@Length(max = 1000, message = "备注长度不能超过2000")
	private String remark;

	/**
	 * 人员状态  1在职；2离职；3公休
	 */
	@NotNull(message = "人员状态不可为空")
	@Enumerated(value = EnumType.STRING)
	private State state;
	
	/**
	 * 人员分类 1医生；2护士；3行政；4其他
	 */
	@NotNull(message = "人员分类不可为空")
	@Enumerated(value = EnumType.STRING)
	private Category category;
	
	/**
	 * 人员性质 1自有；2借调；3临时；4外派
	 */
	@NotNull(message = "人员性质不可为空")
	@Enumerated(value = EnumType.STRING)
	private Nature nature;

	/**
	 * 部门
	 */
	private Department department;

	/**
	 * 部门ID
	 */
	private String departmentId;

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "EN_NAME")
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Column(name = "GENDER")
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Column(name = "ID_NUMBER")
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Column(name = "COUNTRY")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "NATIONALITY")
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "BIRTH_PLACE")
	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	@Column(name = "BIRTH_DATE")
	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "MOBILE_NUMBER")
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Column(name = "OFFICE_NUMBER")
	public String getOfficeNumber() {
		return officeNumber;
	}

	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}


	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STATE")
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Column(name = "CATEGORY")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name = "NATURE")
	public Nature getNature() {
		return nature;
	}

	public void setNature(Nature nature) {
		this.nature = nature;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Column(name = "DEPARTMENT_ID")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
}