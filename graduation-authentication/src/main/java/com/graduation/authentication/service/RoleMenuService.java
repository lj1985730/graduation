package com.graduation.authentication.service;

import com.graduation.authentication.entity.Menu;
import com.graduation.authentication.entity.RoleMenu;
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
 * 权限-角色菜单关系-业务层
 * @author Liu Jun
 * @version 2016-8-14 22:36:02
 */
@Service
@Transactional
public class RoleMenuService extends BaseService<RoleMenu> {

	/**
	 * 获取角色的菜单
	 * @param roleId 角色
	 * @return 角色的菜单
     */
	public List<Menu> getRoleMenu(String roleId) {
		DetachedCriteria roleMenuCriteria = DetachedCriteria.forClass(RoleMenu.class);
		roleMenuCriteria.add(Restrictions.eq("roleId", roleId));
		roleMenuCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		roleMenuCriteria.setProjection(Projections.property("menuId"));

		DetachedCriteria menuCriteria = DetachedCriteria.forClass(Menu.class);
		menuCriteria.add(Property.forName("id").eq(roleMenuCriteria));
		return dao.criteriaQuery(menuCriteria);
	}

	/**
	 * 设置角色的菜单
	 * @param roleId 账户Id
	 * @param menuIds 角色Id集合
	 */
	public void setRoleMenu(String roleId, String[] menuIds) {
		List<String> removedIdList = removeAllMenu(roleId);	//先删除全部已绑定的数据

		List<String> newIdList = Arrays.asList(menuIds);	//需要绑定的数据

		List<String> updateIdList = new ArrayList<>();	//绑定时需要更新的数据
		List<String> createIdList = new ArrayList<>();	//绑定时需要新增的数据

		newIdList.forEach(newId -> {	//saveOrUpdate分发
			if(removedIdList.contains(newId)) {
				updateIdList.add(newId);
			} else {
				createIdList.add(newId);
			}
		});

		updateRoleMenu(roleId, updateIdList);

		createRoleMenu(roleId, createIdList);
	}

	/**
	 * 删除角色的全部菜单
	 * @param roleId 角色Id
	 * @return 被删除的菜单Id
	 */
	private List<String> removeAllMenu(String roleId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RoleMenu.class);
		// 构造条件
		criteria.add(Restrictions.eq("roleId", roleId));
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		List<RoleMenu> list = dao.criteriaQuery(criteria);

		List<String> removedMenuId = new ArrayList<>();

		list.forEach(roleMenu -> {
			this.delete(roleMenu);
			removedMenuId.add(roleMenu.getMenuId());
		});

		return removedMenuId;
	}

	/**
	 * 生效角色的全部菜单
	 * @param roleId 角色Id
	 * @param menuIdList 待操作菜单Id集合
	 */
	private void updateRoleMenu(String roleId, List<String> menuIdList) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RoleMenu.class);
		// 构造条件
		criteria.add(Restrictions.eq("roleId", roleId));
		criteria.add(Restrictions.in("menuId", menuIdList));
		criteria.add(Restrictions.eq(DELETE_PARAM, true));
		List<RoleMenu> list = dao.criteriaQuery(criteria);

		list.forEach(this :: update);
	}

	/**
	 * 新增角色的全部菜单
	 * @param roleId		角色Id
	 * @param menuIdList 待操作菜单Id集合
	 */
	private void createRoleMenu(String roleId, List<String> menuIdList) {
		RoleMenu roleMenu;
		for(String menuId : menuIdList) {
			roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			this.create(roleMenu);
		}
	}



	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(RoleMenu entity) {}

	@Override
	protected void beforeDelete(RoleMenu entity) {}

	@Override
	protected void beforeUpdate(RoleMenu entity) {}
}