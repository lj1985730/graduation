package com.graduation.authentication.entity;

import com.graduation.core.base.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * 权限-系统-实体
 * @author Liu Jun at 2017-10-01 23:14:21
 * @since v1.0.0
 */
@Entity
@Table(name = "T_AUTH_SYSTEM")
public class System extends TreeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单资源标识符
     */
    @Length(max = 500, message = "菜单地址长度不能超过200")
    private String uri;

    /**
     * 描述
     */
    @Length(max = 1000, message = "帮助长度不能超过1000")
    private String description;

    /**
     * 备注
     */
    @Length(max = 1000, message = "备注长度不能超过1000")
    private String remark;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID", insertable = false, updatable = false, unique = true)
    public System getParent() {
        return (System)parent;
    }

    public <E extends TreeEntity> void setParent(E parent) {
        this.parent = parent;
    }

    @Column(name = "URI")
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}