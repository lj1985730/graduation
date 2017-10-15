package com.graduation.core.system.entity;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 通用-系统下拉框-实体
 * @author Liu Jun
 * @since V1.0.0
 */
@Entity
@Table(name = "T_SYS_COMBO_DATA")
public class SystemComboData extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 配置键
	 */
	@NotBlank(message = "配置键不能为空")
	@Length(max = 200, message = "'配置键'长度不能超过200")
	private String businessKey;

	/**
	 * 主体内容
	 */
    @NotBlank(message = "主体内容不能为空")
	private String content;

    /**
     * 条件
     */
    private String conditions;

    /**
     * 排序
     */
    private String orderBy;

    @Column(name = "BUSINESS_KEY")
    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "CONDITIONS")
    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    @Column(name = "ORDERBY")
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}