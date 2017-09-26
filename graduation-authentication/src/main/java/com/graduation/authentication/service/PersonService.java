package com.graduation.authentication.service;

import com.graduation.authentication.entity.Person;
import com.graduation.core.base.exception.BusinessException;
import com.graduation.core.base.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 权限-人员-业务层
 * @author Liu Jun
 * @version 2016-8-12 00:35:32
 */
@Service
@Transactional
public class PersonService extends BaseService<Person> {


	/**
	 * 判断人员是否存在
	 * @return true 存在；false 不存在
	 */
	private Boolean exist(Person person) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Person.class);
		// 构造条件
		criteria.add(Restrictions.disjunction()
				.add(Restrictions.eq("idNo", person.getIdNo()))	//身份证号相同，或者姓名与出生日期均相同
				.add(Restrictions.or(Restrictions.eq("name", person.getName()), Restrictions.eq("birthday", person.getBirthday()))));
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		if(StringUtils.isBlank(person.getId())) {
			criteria.add(Restrictions.ne("id", person.getId()));
		}
		return dao.getCountByCriteria(criteria) > 0;
	}

	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(Person entity) {
		if(exist(entity)) {
			throw new BusinessException("该人员已存在！");
		}
	}

	@Override
	protected void beforeDelete(Person entity) {}

	@Override
	protected void beforeUpdate(Person entity) {
		if(exist(entity)) {
			throw new BusinessException("该人员已存在！");
		}
	}
}