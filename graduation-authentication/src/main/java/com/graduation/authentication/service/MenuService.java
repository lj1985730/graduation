package com.graduation.authentication.service;

import com.graduation.authentication.entity.*;
import com.graduation.core.base.exception.BusinessException;
import com.graduation.core.base.service.TreeService;
import com.graduation.core.base.util.WebUtil;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 权限-菜单-业务层
 * @author Liu Jun
 * @version 2016-7-31 14:55:07
 */
@Service
@Transactional
public class MenuService extends TreeService<Menu> {

	/**
	 * 获得权限下菜单
	 * @return 用户菜单
     */
	public List<Menu> getAccountMenu() {
		return (List<Menu>) WebUtil.getInfo(WebUtil.LoginInfo.MENU_LIST);
	}

	/**
	 * 根据用户权限获取系统菜单(前台)——非管理员登录时加载菜单使用<br />
	 * 关系链路Account -> AccountRole -> Role -> RoleMenu -> Menu
	 * @param accountId 用户Id
	 * @return	用户菜单
	 */
	public List<Menu> getUserMenu(String accountId) {
		DetachedCriteria accountCriteria = DetachedCriteria.forClass(Account.class);	//账户子查询
		accountCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		accountCriteria.add(Restrictions.eq("id", accountId));
		accountCriteria.setProjection(Projections.property("id"));

		DetachedCriteria accountRoleCriteria = DetachedCriteria.forClass(AccountRole.class);	//账户角色关系子查询
		accountRoleCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		accountRoleCriteria.add(Property.forName("accountId").eq(accountCriteria));
		accountRoleCriteria.setProjection(Projections.property("roleId"));

		DetachedCriteria roleCriteria = DetachedCriteria.forClass(Role.class);	//角色子查询
		roleCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		roleCriteria.add(Property.forName("id").eq(accountRoleCriteria));
		roleCriteria.setProjection(Projections.property("id"));

		DetachedCriteria roleMenuCriteria = DetachedCriteria.forClass(RoleMenu.class);	//角色菜单关系子查询
		roleMenuCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		roleMenuCriteria.add(Property.forName("roleId").eq(roleCriteria));
		roleMenuCriteria.setProjection(Projections.property("menuId"));

		DetachedCriteria menuCriteria = DetachedCriteria.forClass(Menu.class);	//菜单查询
		menuCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		menuCriteria.add(Property.forName("id").eq(roleMenuCriteria));

		return dao.criteriaQuery(menuCriteria);
	}
	
	/**
	 * 获取企业下全部前台菜单——管理员登录前台时加载菜单使用
	 * @param system 系统
	 * @return 全部前台菜单
	 */
	public List<Menu> getAllFrontMenu(Menu.SystemType system) {
		return getAllMenu(system, true);
	}
	
	/**
	 * 获取企业下全部后台菜单，管理员登录前台时加载菜单使用
	 * @param system 系统
	 * @return 全部后台菜单
	 */
	public List<Menu> getAllBackMenu(Menu.SystemType system) {
		return getAllMenu(system, false);
	}
	
	/**
	 * 获取企业下全部菜单
	 * @param system 系统
	 * @param isPublic	true 前台；false 后台
	 * @return 全部企业菜单
	 */
	private List<Menu> getAllMenu(Menu.SystemType system, Boolean isPublic) {
		DetachedCriteria menuCriteria = DetachedCriteria.forClass(Menu.class);	//菜单查询
		menuCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		if(isPublic) {	//前台或后台
			menuCriteria.add(Restrictions.in("isPublic", 0, 1));
		} else {
			menuCriteria.add(Restrictions.in("isPublic", 0, 2));
		}
		menuCriteria.add(Restrictions.in("systemType", Menu.SystemType.ALL, system));	//所属系统
		menuCriteria.addOrder(Order.asc("sortNo"));	//排序

		return dao.criteriaQuery(menuCriteria);
	}
	
	/**
	 * 获取企业下全部可分配菜单，功能角色分配菜单时使用
	 * @param system 系统
	 * @return 全部可分配菜单
	 */
	public List<Menu> getAllAssignableMenu(Menu.SystemType system) {
		DetachedCriteria menuCriteria = DetachedCriteria.forClass(Menu.class);	//菜单查询
		menuCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		menuCriteria.add(Restrictions.in("isPublic", 0, 1));
		menuCriteria.add(Restrictions.in("assignable", true));
		menuCriteria.add(Restrictions.in("systemType", Menu.SystemType.ALL, system));	//所属系统
		menuCriteria.addOrder(Order.asc("sortNo"));	//排序

		return dao.criteriaQuery(menuCriteria);
	}

	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(Menu entity) {}

	@Override
	protected void beforeDelete(Menu entity) {
		if(!isLeaf(entity.getId())) {
			throw new BusinessException("删除失败,该数据存在子数据！");
		}
	}

	@Override
	protected void beforeUpdate(Menu entity) {}
}