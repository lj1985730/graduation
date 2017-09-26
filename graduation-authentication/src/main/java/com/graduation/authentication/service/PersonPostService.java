package com.graduation.authentication.service;

import com.graduation.authentication.entity.PersonPost;
import com.graduation.authentication.entity.Post;
import com.graduation.core.base.service.BaseService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 权限-人员岗位关系-业务层
 * @author Liu Jun
 * @version 2016-8-16 22:30:34
 */
@Service
@Transactional
public class PersonPostService extends BaseService<PersonPost> {

	/**
	 * 获取人员的岗位
	 * @param personId 人员Id
	 * @return 人员的岗位
	 */
	public List<Post> getPersonPost(String personId) {
		DetachedCriteria personPostCriteria = DetachedCriteria.forClass(PersonPost.class);
		personPostCriteria.add(Restrictions.eq("personId", personId));
		personPostCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		personPostCriteria.setProjection(Projections.property("postId"));

		DetachedCriteria postCriteria = DetachedCriteria.forClass(Post.class);
		postCriteria.add(Property.forName("id").eq(personPostCriteria));
		return dao.criteriaQuery(postCriteria);
	}

	/**
	 * 设置人员的岗位
	 * @param personId 人员Id
	 * @param postIds 岗位Id集合
	 */
	public void setPersonPost(String personId, String[] postIds) {
		List<String> removedIdList = removeAllPost(personId);	//先删除全部已绑定的数据

		List<String> newIdList = Arrays.asList(postIds);	//需要绑定的数据

		List<String> updateIdList = new ArrayList<>();	//绑定时需要更新的数据
		List<String> createIdList = new ArrayList<>();	//绑定时需要新增的数据

		newIdList.forEach(newId -> {	//saveOrUpdate分发
			if(removedIdList.contains(newId)) {
				updateIdList.add(newId);
			} else {
				createIdList.add(newId);
			}
		});

		updatePersonPost(personId, updateIdList);

		createPersonPost(personId, createIdList);
	}

	/**
	 * 删除人员的全部岗位
	 * @param personId 人员Id
	 * @return 被删除的岗位Id
	 */
	private List<String> removeAllPost(String personId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PersonPost.class);
		// 构造条件
		criteria.add(Restrictions.eq("personId", personId));
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		List<PersonPost> list = dao.criteriaQuery(criteria);

		List<String> removedId = new ArrayList<>();

		list.forEach(roleMenu -> {
			this.delete(roleMenu);
			removedId.add(roleMenu.getPostId());
		});

		return removedId;
	}

	/**
	 * 生效人员的全部岗位
	 * @param personId 人员Id
	 * @param postIdList 待操作岗位Id集合
	 */
	private void updatePersonPost(String personId, List<String> postIdList) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PersonPost.class);
		// 构造条件
		criteria.add(Restrictions.eq("personId", personId));
		criteria.add(Restrictions.in("postId", postIdList));
		criteria.add(Restrictions.eq(DELETE_PARAM, true));
		List<PersonPost> list = dao.criteriaQuery(criteria);

		list.forEach(this :: update);
	}

	/**
	 * 新增人员的全部岗位
	 * @param personId 人员Id
	 * @param postIdList 待操作岗位Id集合
	 */
	private void createPersonPost(String personId, List<String> postIdList) {
		PersonPost personPost;
		for(String postId : postIdList) {
			personPost = new PersonPost();
			personPost.setPersonId(personId);
			personPost.setPostId(postId);
			this.create(personPost);
		}
	}

	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(PersonPost entity) {}

	@Override
	protected void beforeDelete(PersonPost entity) {}

	@Override
	protected void beforeUpdate(PersonPost entity) {}
}