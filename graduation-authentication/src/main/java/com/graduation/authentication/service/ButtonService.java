package com.graduation.authentication.service;

import com.graduation.authentication.entity.Button;
import com.graduation.authentication.entity.RoleButton;
import com.graduation.core.base.exception.BusinessException;
import com.graduation.core.base.service.BaseService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 权限-按钮-业务层
 * @author Liu Jun
 * @version 2016-8-8 23:38:18
 */
@Service
@Transactional
public class ButtonService extends BaseService<Button> {
	
	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(Button entity) {}

	@Override
	protected void beforeDelete(Button entity) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RoleButton.class);
		// 构造条件
		criteria.add(Restrictions.eq("buttonId", entity.getId()));
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		if (dao.getCountByCriteria(criteria) > 0) {
			throw new BusinessException("删除失败,该数据在使用中");
		}
	}

	@Override
	protected void beforeUpdate(Button entity) {}
}