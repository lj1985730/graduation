package com.graduation.authentication.service;

import com.graduation.authentication.entity.Menu;
import com.graduation.authentication.entity.Module;
import com.graduation.core.base.service.BaseService;
import com.graduation.core.base.util.CookieUtil;
import com.graduation.core.base.util.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限-用户便捷菜单-业务层
 * @author liujun
 * @since 2016-3-4 11:10:59
 */
@Service
@Transactional
public class ModuleService extends BaseService<Module> {
	
	/**
	 * 获取上次访问记录
	 * @param request 请求体
	 * @return 上次访问记录列表
	 */
	public List<Menu> getLastMenu(HttpServletRequest request) {
		//上次访问记录列表
		List<Menu> lastMenuList = new ArrayList<>();
		
		//取上次访问页面
		Cookie lastMenuCookie = CookieUtil.getCookie(request, (CookieUtil.COOKIE_LAST_MENU_KEY));
		if(lastMenuCookie != null && !StringUtils.isBlank(lastMenuCookie.getValue())) {
			DetachedCriteria lastCriteria = DetachedCriteria.forClass(Menu.class);
			lastCriteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));
			lastCriteria.add(Restrictions.eq("id", lastMenuCookie.getValue()));
			lastMenuList = this.searchByCriteria(lastCriteria);
		}
		
		//没有上一次访问记录，占位
		if(lastMenuList.size() == 0) {
			Menu menu = new Menu();
			menu.setName("无上次记录");
			menu.setUrl("home");
			menu.setId("00000000-0000-0000-0000-000000000000");
			lastMenuList.add(menu);
		}
		
		return lastMenuList;
	}

	/**
	 * 获取快速访问菜单
	 * @return 快速访问菜单
	 */
	public List<Menu> getQuickMenu() {
		//结果列表
		List<Menu> quickMenuList = new ArrayList<>();
		
		List<Module> quickModules = this.getQuickModule();
		
		//将快速访问页面加入队列
		quickModules.forEach(quickModule -> quickMenuList.add(quickModule.getMenu()));

		return quickMenuList;
	}
	
	/**
	 * 获取快速访问菜单（关系对象）
	 * @return 快速访问菜单（关系对象）
	 */
	public List<Module> getQuickModule() {
		//取快速访问页面
		DetachedCriteria criteria = DetachedCriteria.forClass(Module.class);
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		criteria.add(Restrictions.eq("accountId", WebUtil.getInfo(WebUtil.LoginInfo.ACCOUNT_ID).toString()));
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
		List<Module> oldModules = this.getQuickModule();
		//删除全部旧数据
		if(oldModules != null && !oldModules.isEmpty()) {
			oldModules.forEach(oldModule -> {
				super.destory(oldModule.getId());	//物理删除
			});
		}

		//新增数据
		Module module;
		for (String menuId : menuIds) {
			module = new Module();
			module.setMenuId(menuId);
			this.create(module);
		}
	}
	
	/**
	 * 删除快捷菜单，物理删除
	 */
	public void delete(Serializable id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Module.class);
		criteria.add(Restrictions.eq("accountId", WebUtil.getInfo(WebUtil.LoginInfo.ACCOUNT_ID).toString()));
		criteria.add(Restrictions.eq("menuId", id));
		List<Module> quickModules = this.searchByCriteria(criteria);

		quickModules.forEach(quickModule -> {
			super.destory(quickModule.getId());	//物理删除
		});
	}
	
	@Override
	public List<Map<String, String>> genTotalRow() {
		return null;	//自定义合计行内容
	}

	@Override
	public void beforeCreate(Module entity) {
		entity.setAccountId(WebUtil.getInfo(WebUtil.LoginInfo.ACCOUNT_ID).toString());
	}

	@Override
	public void beforeDelete(Module entity) {}

	@Override
	public void beforeUpdate(Module entity) {}
}