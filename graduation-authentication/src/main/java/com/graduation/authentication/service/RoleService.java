package com.graduation.authentication.service;

import com.graduation.authentication.entity.*;
import com.graduation.core.base.dto.Page;
import com.graduation.core.base.exception.BusinessException;
import com.graduation.core.base.service.BaseService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 权限-角色-业务层
 * @author Liu Jun
 * @version 2016-8-14 22:36:02
 */
@Service
@Transactional
public class RoleService extends BaseService<Role> {
	
	@Resource
	private RoleButtonService roleBtnService;
	
	@Resource
	private RoleMenuService roleMenuService;

	/**
	 * 不分页查询btn表
	 * @param criteria 查询体
	 * @return 全部数据
	 */
	public Page<Button> searchAllInPage(DetachedCriteria criteria) {

		List<Button> list = dao.criteriaQuery(criteria);	// 执行查询

		Page<Button> pager = new Page<>(0, list.size());	// 创建页
		pager.setRows(list);

		return pager;
	}

	/**
	 * 角色是否存在绑定菜单
	 * @param roleId 角色ID
	 * @return true 存在；false 不存在
     */
	private Boolean existMenuBinded(String roleId) {
		return existDataBinded(roleId, RoleMenu.class, "menu");
	}

	/**
	 * 角色是否存在绑定按钮
	 * @param roleId 角色ID
	 * @return true 存在；false 不存在
     */
	private Boolean existButtonBinded(String roleId) {
		return existDataBinded(roleId, RoleButton.class, "button");
	}

	/**
	 * 角色是否存在绑定账户
	 * @param roleId 角色ID
	 * @return true 存在；false 不存在
     */
	private Boolean existAccountBinded(String roleId) {
		return existDataBinded(roleId, AccountRole.class, "account");
	}

	/**
	 * 角色是否存在绑定数据
	 * @param roleId 角色ID
	 * @param clazz 关系对象
	 * @param relationPath 关系路径，可选，不传参则不加入关联校验
	 * @return true 存在；false 不存在
	 */
	private Boolean existDataBinded(String roleId, Class clazz, String... relationPath) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Restrictions.eq("roleId", roleId));
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		if(relationPath != null && relationPath.length > 0) {
			criteria.createAlias(relationPath[0], relationPath[0]);
			criteria.add(Restrictions.eq(relationPath[0] + "." + DELETE_PARAM, false));
		}
		return dao.getCountByCriteria(criteria) > 0;
	}

	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(Role entity) {}

	@Override
	protected void beforeDelete(Role entity) {
		if(existMenuBinded(entity.getId())) {
			throw new BusinessException("存在菜单绑定关系，不可删除！");
		}
		if(existButtonBinded(entity.getId())) {
			throw new BusinessException("存在按钮绑定关系，不可删除！");
		}
		if(existAccountBinded(entity.getId())) {
			throw new BusinessException("存在账户绑定关系，不可删除！");
		}
	}

	@Override
	protected void beforeUpdate(Role entity) {}
}