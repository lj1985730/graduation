package com.graduation.authentication.service;

import com.graduation.authentication.entity.Menu;
import com.graduation.authentication.entity.QuickMenu;
import com.graduation.authentication.util.AuthenticationUtils;
import com.graduation.core.base.service.BaseService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限-用户便捷菜单-业务层
 * @author liujun at 2016-3-4 11:10:59
 * @version v1.0.0
 */
@Service
@Transactional
public class QuickMenuService extends BaseService<QuickMenu> {

	/**
	 * 获取快速访问菜单
	 * @return 快速访问菜单
	 */
	public List<Menu> getQuickMenu() {
		//结果列表
		List<Menu> quickMenuList = new ArrayList<>();
		
		List<QuickMenu> quickModules = this.getQuickMenuModel();
		
		//将快速访问页面加入队列
		quickModules.forEach(quickModule -> quickMenuList.add(quickModule.getMenu()));

		return quickMenuList;
	}
	
	/**
	 * 获取快速访问菜单（关系对象）
	 * @return 快速访问菜单（关系对象）
	 */
	public List<QuickMenu> getQuickMenuModel() {
		//取快速访问页面
		DetachedCriteria criteria = DetachedCriteria.forClass(QuickMenu.class);
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		criteria.add(Restrictions.eq("accountId", AuthenticationUtils.getInfo(AuthenticationUtils.LoginInfo.ACCOUNT_ID).toString()));
		criteria.addOrder(Order.desc("lastModifyTime"));	//默认操作时间倒序
		return this.searchByCriteria(criteria);
	}
	
	/**
	 * 保存快捷表单
	 * @param menuIds	菜单id数组
	 */
	public void createQuickMenu(String[] menuIds) {
		int count = menuIds.length;
		if(count == 0) {
			return;
		}
		//取旧数据
		List<QuickMenu> oldQuickMenus = this.getQuickMenuModel();
		//删除全部旧数据
		if(oldQuickMenus != null && !oldQuickMenus.isEmpty()) {
			oldQuickMenus.forEach(oldQuickMenu -> {
				super.destroy(oldQuickMenu.getId());	//物理删除
			});
		}

		//新增数据
		QuickMenu quickMenu;
		for (String menuId : menuIds) {
			quickMenu = new QuickMenu();
			quickMenu.setMenuId(menuId);
			this.create(quickMenu);
		}
	}
	
	/**
	 * 删除快捷菜单，物理删除
	 */
	public void delete(Serializable id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(QuickMenu.class);
		criteria.add(Restrictions.eq("accountId", AuthenticationUtils.getInfo(AuthenticationUtils.LoginInfo.ACCOUNT_ID).toString()));
		criteria.add(Restrictions.eq("menuId", id));
		List<QuickMenu> quickModules = this.searchByCriteria(criteria);

		quickModules.forEach(quickModule -> {
			super.destroy(quickModule.getId());	//物理删除
		});
	}
	
	@Override
	public List<Map<String, String>> genTotalRow() {
		return null;	//自定义合计行内容
	}

	@Override
	public void beforeCreate(QuickMenu entity) {
		entity.setAccountId(AuthenticationUtils.getInfo(AuthenticationUtils.LoginInfo.ACCOUNT_ID).toString());
	}

	@Override
	public void beforeDelete(QuickMenu entity) {}

	@Override
	public void beforeUpdate(QuickMenu entity) {}
}