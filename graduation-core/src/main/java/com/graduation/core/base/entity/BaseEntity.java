package com.graduation.core.base.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * 实体基类，提供被其他实体类继承<p>
 * 改写方式：子类类定义声明重写属性注解,例如：<p>
 * <PRE>@AttributeOverrides({</PRE>
 * <PRE>@AttributeOverride(name = "id", column = @Column(name = "spare_Id", nullable = false, unique = true, columnDefinition = "char(36)"))})</PRE><p>
 * @author Liu jun
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	protected static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	protected String id;

	/**
	 * 操作人账户Id
	 */
	protected String lastModifyAccountId;

	/**
	 * 操作时间
	 */
	@NotNull(message = "操作时间不可为空！")
	protected LocalDateTime lastModifyTime;

    /**
     * 是否删除
     */
	@NotNull(message = "删除标记不可为空！")
	protected Boolean deleted;

	@Id
	@GeneratedValue(generator = "departmentGenerator")
	@GenericGenerator(name = "departmentGenerator", strategy = "uuid2")
	@Column(name = "ID", nullable = false, unique = true, columnDefinition = "char(36)")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "LAST_MODIFY_ACCOUNT_ID", columnDefinition = "varchar(100)")
	public String getLastModifyAccountId() {
		return lastModifyAccountId;
	}

	public void setLastModifyAccountId(String lastModifyAccountId) {
		this.lastModifyAccountId = lastModifyAccountId;
	}

	@Column(name = "LAST_MODIFY_TIME")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public LocalDateTime getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(LocalDateTime lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Column(name = "DELETED")
	@Type(type = "yes_no")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * 通用toString方法
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * 通用hashCode方法
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * 通用equals方法
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof BaseEntity && EqualsBuilder.reflectionEquals(this, obj);
	}
}