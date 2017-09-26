package com.graduation.authentication.controller;

import com.graduation.authentication.service.RoleButtonService;
import com.graduation.authentication.service.RoleService;
import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.service.BaseService;
import com.graduation.authentication.entity.Role;
import com.graduation.authentication.service.RoleMenuService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限-角色-控制层
 * @author Liu Jun
 * @version 2016-8-16 20:47:42
 */
@Controller
@RequestMapping(value = "**/system/authentication/role")
class RoleController extends BaseController {
	
	@Resource
	private RoleService service;
	
	@Resource
	private RoleMenuService roleMenuService;

	@Resource
	private RoleButtonService roleButtonService;

	/**
	 * 页面的首页
	 */
	@RequestMapping(value = { "/index" })
	public String index() {
		return pageView("/system/authentication/role");
	}

	/**
	 * 查询分页列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/" })
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		int offset = Integer.parseInt(request.getParameter("offset"));	//获取分页信息
		int limit = Integer.parseInt(request.getParameter("limit"));

		DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);	//构造DetachedCriteria对象
		criteria.createAlias("department", "department", JoinType.LEFT_OUTER_JOIN);

		criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));

		String searchText = request.getParameter("search");	//输入框查询
		if(!StringUtils.isBlank(searchText)) {
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.like("roleName", searchText, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("roleCode", searchText, MatchMode.ANYWHERE));
			criteria.add(disjunction);
		}

		//高级查询
		String roleCode = request.getParameter("roleCode");
		if (!StringUtils.isBlank(roleCode)) {
			criteria.add(Restrictions.like("roleCode", roleCode, MatchMode.ANYWHERE));
		}
		String roleName = request.getParameter("roleName");
		if (!StringUtils.isBlank(roleName)) {
			criteria.add(Restrictions.like("roleName", roleName, MatchMode.ANYWHERE));
		}
		//处理排序
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		if(!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			if("roleName".equals(sort)||"department.text".equals(sort)){
				service.addPYOrder(sort, order);
			} else {
				service.addOrder(sort, order);
			}
		} else {	//默认排序
			service.addOrder("asc", "roleName");
		}
		return new JsonResult(service.searchPage(criteria, offset, limit));
    }

	/**
	 * 新增数据
	 * @return 操作结果
	 */
    @RequestMapping(value = { "/" }, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody Role role) {
        service.create(role);
		return new JsonResult(JsonResult.CREATE_SUCCEED);
    }

	/**
	 * 更新数据
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult modify(@RequestBody Role role) {
		service.update(role);
		return new JsonResult(JsonResult.UPDATE_SUCCEED);
	}

	/**
	 * 删除数据
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResult delete(@PathVariable("id") String id) {
		service.delete(id);
		return new JsonResult(JsonResult.DELETE_SUCCEED);
	}

	/**
	 * 根据角色查询菜单
	 * @param roleId 角色Id
	 * @return 角色菜单
	 */
	@RequestMapping(value = { "/{roleId}/menu" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchRoleMenu(@PathVariable("roleId") String roleId) {
		return new JsonResult(roleMenuService.getRoleMenu(roleId));
	}

	/**
	 * 设置角色菜单
	 * @param request 请求体
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/menu" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult setRoleMenu(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String menuIds = request.getParameter("menuIds");
		String[] menuIdArr = menuIds.split(",");
		roleMenuService.setRoleMenu(roleId, menuIdArr);
		return new JsonResult(true, "保存成功！");
	}

	/**
	 * 根据角色查询按钮
	 * @param roleId 角色Id
	 * @return 角色菜单
	 */
	@RequestMapping(value = { "/{roleId}/button" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchRoleButton(@PathVariable("roleId") String roleId) {
		return new JsonResult(roleButtonService.getRoleButton(roleId));
	}

	/**
	 * 设置角色菜单
	 * @param request 请求体
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/button" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult setRoleButton(HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String buttonIds = request.getParameter("buttonIds");
		String[] buttonIdArr = buttonIds.split(",");
		roleButtonService.setRoleButton(roleId, buttonIdArr);
		return new JsonResult(true, "保存成功！");
	}
}