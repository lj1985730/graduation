package com.graduation.authentication.service;

import com.graduation.authentication.entity.*;
import com.graduation.authentication.base.service.SystemLoader;
import com.graduation.core.base.entity.BaseEntity;
import com.graduation.core.base.exception.BusinessException;
import com.graduation.core.base.service.BaseService;
import com.graduation.core.base.util.EntityUtil;
import com.graduation.core.base.util.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 权限-登录-业务层
 * @author Liu Jun
 * @version 2016-7-31 15:00:27
 */
@Service
@Transactional
public class LoginService extends BaseService<BaseEntity> {

	@Resource
	private MenuService menuService;
	
	/**
	 * 通用拼接字符串分隔符
	 */
	private static final String separator = ",";
	
	/**
	 * 登录权限验证
	 * @param account 待验证账号
	 */
	public void validLoginAuth(Account account) {
		if(account.getSuperAccount()) {	//超管直接通过
			return;
		}
		validAccount(account);	//账户校验
		validPerson(account);	//人员校验
	}

	/**
	 * 验证账号信息
	 * @param account 待验证账号
	 */
	private void validAccount(Account account) {
		if(account.getDeleted()) {
			throw new BusinessException("登录失败，该账户已删除！");
		}
		if(!account.getEnabled()) {
			throw new BusinessException("登录失败，该账户被禁用！");
		}
	}

	/**
	 * 验证人员信息
	 * @param account 待验证账号
	 */
	private void validPerson(Account account) {
		Person person = account.getPerson();
		if(person.getDeleted() == null || person.getDeleted()) {
			throw new BusinessException("登录失败，该人员已删除！");
		}
		if(person.getState() == null || person.getState() == Person.State.LEAVE) {
			throw new BusinessException("登录失败，该人员已离职！");
		}
		if(person.getCategory() == null) {
			throw new BusinessException("登录失败，该人员类型信息缺失！");
		}
	}

	/**
	 * 写入账户信息
	 * @param account 待写入账户信息
	 */
	public void bookLoginInfo(Account account) {
		bookRoleInfo(account);	//角色信息
		if(!account.getSuperAccount()){	//判断不是超级管理员
			bookPostInfo(account);	//岗位信息
			bookPersonInfo(account);	//人员信息
		}
		bookAccountInfo(account);	//账户信息
		bookMenuInfo(account);	//菜单信息
	}

	/**
	 * 写入账户信息-后台登录
	 * @param account 待写入账户信息
	 */
	public void bookBackLoginInfo(Account account) {
		bookRoleInfo(account);	//角色信息
		bookAccountInfo(account);	//账户信息
		bookBackMenuInfo(account);	//后台菜单信息
	}

	/**
	 * 记录角色信息
	 * @param account 账户信息
	 */
	private void bookRoleInfo(Account account) {
		List<Role> roles = getRoleByAccount(account.getId());
		if(roles == null || roles.isEmpty()) {
			return;
		}
		String roleIdStr = "";
		String[] roleIdArr = new String[roles.size()];
		for (int i = 0; i < roles.size(); i++) {
			roleIdStr += roles.get(i).getId() + separator;
			roleIdArr[i] = roles.get(i).getId();
		}
		WebUtil.setInfo(WebUtil.LoginInfo.ROLE_ID, StringUtils.removeEnd(roleIdStr, separator));
		WebUtil.setInfo(WebUtil.LoginInfo.ROLE_ID_ARR, roleIdArr);
	}

	/**
	 * 记录岗位信息
	 * @param account 账户信息
	 */
	private void bookPostInfo(Account account) {
		Person person = account.getPerson();
		List<Post> posts = getPostByPerson(person.getId());
		if(posts == null || posts.isEmpty()) {
			return;
		}
		String postIdStr = "";
		String[] postIdArr = new String[posts.size()];
		for (int i = 0; i < posts.size(); i++) {
			postIdStr += posts.get(i).getId() + separator;
			postIdArr[i] = posts.get(i).getId();
		}
		WebUtil.setInfo(WebUtil.LoginInfo.POST_ID, StringUtils.removeEnd(postIdStr, separator));
		WebUtil.setInfo(WebUtil.LoginInfo.POST_ID_ARR, postIdArr);
	}

	/**
	 * 记录人员信息
	 * @param account 账户信息
	 */
	private void bookPersonInfo(Account account) {
		Person person = account.getPerson();
		WebUtil.setInfo(WebUtil.LoginInfo.PERSON_ID, person.getId());
		WebUtil.setInfo(WebUtil.LoginInfo.PERSON_NAME, person.getName());
		WebUtil.setInfo(WebUtil.LoginInfo.PERSON_EMAIL, person.getEmail());
	}

	/**
	 * 记录账户信息
	 * @param account 账户信息
	 */
	private void bookAccountInfo(Account account) {
		WebUtil.setInfo(WebUtil.LoginInfo.ACCOUNT_ID, account.getId());
		WebUtil.setInfo(WebUtil.LoginInfo.ACCOUNT_NAME, account.getName());
		WebUtil.setInfo(WebUtil.LoginInfo.SUPER_ACCOUNT, account.getSuperAccount());
	}

	/**
	 * 记录菜单信息
	 * @param account 账户信息
	 */
	private void bookMenuInfo(Account account) {
		//保存菜单权限列表
		List<Menu> userMenu;
		if(account.getSuperAccount()) {	//超管直接通过
			userMenu = menuService.getAllFrontMenu(Menu.SystemType.ALL);
		} else {
			userMenu = menuService.getUserMenu(account.getId());
		}
		List<String> menuIds = EntityUtil.getAllId(userMenu);
		StringBuilder menuIdStr = new StringBuilder("");
		menuIds.forEach(menuId -> menuIdStr.append(menuId).append(separator));

		WebUtil.setInfo(WebUtil.LoginInfo.MENU_ID_ARR, menuIds.toArray());	//ID List
		WebUtil.setInfo(WebUtil.LoginInfo.MENU_ID, StringUtils.removeEnd(menuIdStr.toString(), separator));	//Menu String
		WebUtil.setInfo(WebUtil.LoginInfo.MENU_LIST, userMenu);	//Menu List
	}


	/**
	 * 记录菜单信息
	 * @param account 账户信息
	 */
	private void bookBackMenuInfo(Account account) {
		//保存菜单权限列表
		List<Menu> userMenu;
		if(!account.getSuperAccount()) {	//超管直接通过
			return;
		} else {
			userMenu = menuService.getAllBackMenu(Menu.SystemType.valueOf(SystemLoader.getConfigValue("SYSTEM", null)));
		}
		List<String> menuIds = EntityUtil.getAllId(userMenu);
		StringBuilder menuIdStr = new StringBuilder("");
		menuIds.forEach(menuId -> menuIdStr.append(menuId).append(separator));

		WebUtil.setInfo(WebUtil.LoginInfo.MENU_ID_ARR, menuIds.toArray());	//ID List
		WebUtil.setInfo(WebUtil.LoginInfo.MENU_ID, StringUtils.removeEnd(menuIdStr.toString(), separator));	//Menu String
		WebUtil.setInfo(WebUtil.LoginInfo.MENU_LIST, userMenu);	//Menu List
	}

	/**
	 * 根据人员Id获取岗位
	 * @param personId 人员ID
	 * @return 岗位对象集合
	 */
	private List<Post> getPostByPerson(String personId) {
		if(StringUtils.isBlank(personId)) {
			return null;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		DetachedCriteria subCriteria = DetachedCriteria.forClass(PersonPost.class);
		subCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		subCriteria.add(Restrictions.eq("personId", personId));
		subCriteria.setProjection(Projections.property("roleId"));
		criteria.add(Property.forName("postId").eq(subCriteria));
		return dao.criteriaQuery(criteria);
	}
	
	/**
	 * 根据账户Id获取角色
	 * @param accountId 账户Id
	 * @return 角色对象集合
	 */
	private List<Role> getRoleByAccount(String accountId) {
		if(StringUtils.isBlank(accountId)) {
			return null;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		DetachedCriteria subCriteria = DetachedCriteria.forClass(AccountRole.class);
		subCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		subCriteria.add(Restrictions.eq("accountId", accountId));
		subCriteria.setProjection(Projections.property("roleId"));
		criteria.add(Property.forName("id").eq(subCriteria));
		return dao.criteriaQuery(criteria);
	}

	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(BaseEntity entity) {}

	@Override
	protected void beforeDelete(BaseEntity entity) {}

	@Override
	protected void beforeUpdate(BaseEntity entity) {}
}