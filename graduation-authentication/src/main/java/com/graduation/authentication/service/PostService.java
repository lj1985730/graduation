package com.graduation.authentication.service;

import com.graduation.core.base.exception.BusinessException;
import com.graduation.core.base.service.BaseService;
import com.graduation.authentication.entity.PersonPost;
import com.graduation.authentication.entity.Post;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 权限-岗位-业务层
 * @author Liu Jun
 * @version 2016-8-14 22:28:14
 */
@Service
@Transactional
public class PostService extends BaseService<Post> {
    
	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(Post entity) {}

	@Override
	protected void beforeDelete(Post entity) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PersonPost.class);
		criteria.createCriteria("person", "person");
		criteria.add(Restrictions.eq("postId", entity.getId()));
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		criteria.add(Restrictions.eq("person." + DELETE_PARAM, false));
		if (dao.getCountByCriteria(criteria) > 0) {
			throw new BusinessException("该数据在使用中...");
		}
	}

	@Override
	protected void beforeUpdate(Post entity) {}
}