package com.graduation.authentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * 权限-人员岗位关系-实体
 * @author Liu Jun
 * @version 2016-7-31 14:14:44
 */
@Entity
@Table(name = "T_AUTH_PERSON_POST")
public class PersonPost extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Person person;

    /**
     * 人员ID
     **/
    @NotBlank(message = "人员ID不能为空")
    @Length(max = 36, message = "人员ID长度不能超过36")
    private String personId;

    private Post post;

    /**
     * 岗位ID
     **/
    @NotBlank(message = "岗位ID不能为空")
    @Length(max = 36, message = "岗位ID长度不能超过36")
    private String postId;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "PERSON_ID", insertable = false, updatable = false, referencedColumnName = "ID")
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

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "POST_ID", insertable = false, updatable = false, referencedColumnName = "ID")
    @JsonIgnore
    public Post getPost() {
        return post;
    }

    public void setPost(Post sysPost) {
        this.post = post;
    }

    @Column(name = "POST_ID", columnDefinition = "char(1)")
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}