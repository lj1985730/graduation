package com.graduation.authentication.service;

import com.graduation.core.base.exception.BusinessException;
import com.graduation.core.base.service.TreeService;
import com.graduation.authentication.entity.Department;
import com.graduation.authentication.entity.Role;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 权限-部门-业务层
 * @author Liu Jun
 * @version 2016-8-11 23:58:24
 */
@Service
@Transactional
public class DepartmentService extends TreeService<Department> {

	/**
	 * 查询最大序号
	 * @param parentId	父节点Id
	 * @return 最大序号
     */
	public Integer searchMaxSort(String parentId) {
		Criteria criteria = DetachedCriteria.forClass(Department.class).getExecutableCriteria(dao.getSession());
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		criteria.add(Restrictions.eq("parentId", parentId));

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.max("sortNo"));
		projectionList.add(Projections.property("sortNo"));
		criteria.setProjection(projectionList);
		List<Integer> maxSort = criteria.list();
		if(maxSort.isEmpty()) {
			return 0;
		} else {
			return maxSort.get(0);
		}
	}

	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(Department entity) { }

	@Override
	protected void beforeDelete(Department entity) {

		if(!isLeaf(entity.getId())) {
			throw new BusinessException("删除失败,该数据存在子数据！");
		}

		DetachedCriteria roleCriteria = DetachedCriteria.forClass(Role.class);
		roleCriteria.add(Restrictions.eq("departmentId", entity.getId()));
		roleCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		if (dao.getCountByCriteria(roleCriteria) > 0) {
			throw new BusinessException("删除失败,该数据已绑定角色！");
		}
	}

	@Override
	protected void beforeUpdate(Department entity) { }
}