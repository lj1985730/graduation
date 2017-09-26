package com.graduation.authentication.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * 权限-岗位-实体
 * @author Liu Jun
 * @version 2016-7-31 14:14:44
 */
@Entity
@Table(name="T_AUTH_POST")
public class Post extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 岗位编码
     **/
    @Length(max = 500, message = "岗位编码长度不能超过500")
    private String code;

    /**
     * 岗位名称
     **/
    @NotBlank(message = "岗位名称不能为空")
    @Length(max = 500, message = "岗位名称长度不能超过500")
    private String name;
    
    /**
     * 是否部门长
     **/
    @NotBlank(message = "是否部门长不能为空")
    private Boolean isLeader;

    /**
     * 部门
     **/
    private Department department;

    /**
     * 部门ID
     **/
    @Length(max = 36, message = "部门ID长度不能超过36")
    private String departmentId;

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "IS_LEADER")
    @Type(type = "yes_no")
    public Boolean getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(Boolean isLeader) {
		this.isLeader = isLeader;
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

    @Column(name = "DEPARTMENT_ID", columnDefinition="char(36)")
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}