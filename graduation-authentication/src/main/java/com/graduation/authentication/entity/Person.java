package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 权限-人员-实体
 * @author Liu Jun
 * @version 2016-7-31 14:14:44
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
	@Length(max = 50, message = "姓名长度不能超过50")
	private String name;

	/**
	 * 英文名
	 */
	@Length(max = 500, message = "英文名长度不能超过500")
	private String enName;

	/**
	 * 性别 1：男；2：女
	 */
	@NotNull(message = "性别不可为空")
	@Enumerated(value=EnumType.STRING)
	private Gender gender;

	/**
	 * 证件类型1.身份证2.驾照3.护照
	 */
	@NotNull(message = "证件类型不可为空")
	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	/**
	 * 证件号
	 */
	@Length(max = 200, message = "证件号长度不能超过200")
	private String idNo;
	
	/**
	 * 国籍
	 */
	@Length(max = 50, message = "国籍长度不能超过50")
	private String country;
	
	/**
	 * 民族
	 */
	@Length(max = 50, message = "民族长度不能超过50")
	private String nationality;

	/**
	 * 生日
	 */
	private LocalDate birthday;

	/**
	 * 手机号码
	 */
	@Length(max = 30, message = "手机号码长度不能超过30")
	private String mobileNo;

	/**
	 * 办公号码
	 */
	@Length(max = 100, message = "办公号码长度不能超过500")
	private String officeNo;

	/**
	 * 邮箱
	 */
	@Length(max = 200, message = "邮箱长度不能超过200")
	private String email;

	/**
	 * 家庭住址
	 */
	@Length(max = 500, message = "家庭住址长度不能超过500")
	private String address;

	/**
	 * 备注
	 */
	@Length(max = 2000, message = "备注长度不能超过2000")
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

	/**
	 * 岗位
	 */
	private Post post;

	/**
	 * 岗位ID
	 */
	private String postId;

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

	@Column(name = "ID_TYPE")
	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	@Column(name = "ID_NO")
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
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

	@Column(name = "BIRTHDAY")
	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	@Column(name = "MOBILE_NO")
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name = "OFFICE_NO")
	public String getOfficeNo() {
		return officeNo;
	}

	public void setOfficeNo(String officeNo) {
		this.officeNo = officeNo;
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

	@Column(name = "CATEGROY")
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POST_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Column(name = "POST_ID")
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
}