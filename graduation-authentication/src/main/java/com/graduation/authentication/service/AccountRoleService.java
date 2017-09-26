package com.graduation.authentication.service;

import com.graduation.authentication.entity.AccountRole;
import com.graduation.core.base.service.BaseService;
import com.graduation.authentication.entity.Role;
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
 * 权限-账户角色关系-业务层
 * @author Liu Jun
 * @version 2016-8-16 22:30:34
 */
@Service
@Transactional
public class AccountRoleService extends BaseService<AccountRole> {

	/**
	 * 获取账户的角色
	 * @param accountId 账户Id
	 * @return 人员的岗位
	 */
	public List<Role> getAccountRole(String accountId) {
		DetachedCriteria accountRoleCriteria = DetachedCriteria.forClass(AccountRole.class);
		accountRoleCriteria.add(Restrictions.eq("accountId", accountId));
		accountRoleCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		accountRoleCriteria.setProjection(Projections.property("roleId"));

		DetachedCriteria roleCriteria = DetachedCriteria.forClass(Role.class);
		roleCriteria.add(Property.forName("id").eq(accountRoleCriteria));
		return dao.criteriaQuery(roleCriteria);
	}

	/**
	 * 设置账户的角色
	 * @param accountId		账户Id
	 * @param roleIds		角色Id集合
	 */
	public void setAccountRole(String accountId, String[] roleIds) {
		List<String> removedRoleIdList = removeAllRole(accountId);	//先删除全部已绑定的角色

		List<String> newRoleIdList = Arrays.asList(roleIds);	//需要绑定的角色

		List<String> updateRoleIdList = new ArrayList<>();	//绑定时需要更新的角色
		List<String> createRoleIdList = new ArrayList<>();	//绑定时需要新增的角色

		newRoleIdList.forEach(newRoleId -> {	//saveOrUpdate分发
			if(removedRoleIdList.contains(newRoleId)) {
				updateRoleIdList.add(newRoleId);
			} else {
				createRoleIdList.add(newRoleId);
			}
		});

		updateAccountRole(accountId, updateRoleIdList);

		createAccountRole(accountId, createRoleIdList);
	}

	/**
	 * 删除账户的全部角色
	 * @param accountId		账户Id
	 * @return 被删除的角色Id
	 */
	private List<String> removeAllRole(String accountId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AccountRole.class);
		// 构造条件
		criteria.add(Restrictions.eq("accountId", accountId));
		criteria.add(Restrictions.eq(DELETE_PARAM, false));

		List<AccountRole> list = dao.criteriaQuery(criteria);

		List<String> removedRoleId = new ArrayList<>();

		list.forEach(accountRole -> {
			this.delete(accountRole);
			removedRoleId.add(accountRole.getRoleId());
		});

		return removedRoleId;
	}

	/**
	 * 生效账户的全部角色
	 * @param accountId		账户Id
	 * @param roleIdList 待操作角色Id集合
	 */
	private void updateAccountRole(String accountId, List<String> roleIdList) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AccountRole.class);
		// 构造条件
		criteria.add(Restrictions.eq("accountId", accountId));
		criteria.add(Restrictions.in("roleId", roleIdList));
		criteria.add(Restrictions.eq(DELETE_PARAM, true));
		List<AccountRole> list = dao.criteriaQuery(criteria);

		list.forEach(this :: update);
	}

	/**
	 * 新增账户的全部角色
	 * @param accountId		账户Id
	 * @param roleIdList 待操作角色Id集合
	 */
	private void createAccountRole(String accountId, List<String> roleIdList) {
		AccountRole accountRole;
		for(String roleId : roleIdList) {
			accountRole = new AccountRole();
			accountRole.setAccountId(accountId);
			accountRole.setRoleId(roleId);
			this.create(accountRole);
		}
	}

	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(AccountRole entity) {}

	@Override
	protected void beforeDelete(AccountRole entity) {}

	@Override
	protected void beforeUpdate(AccountRole entity) {}
}