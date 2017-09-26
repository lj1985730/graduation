package com.graduation.authentication.controller;

import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.service.BaseService;
import com.graduation.core.base.util.WebUtil;
import com.graduation.authentication.entity.Account;
import com.graduation.authentication.service.AccountRoleService;
import com.graduation.authentication.service.AccountService;
import com.graduation.authentication.service.DepartmentService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 权限-账户-控制层
 * @author Liu Jun
 * @version 2016-8-14 23:29:36
 */
@Controller
@RequestMapping(value = "**/system/authentication/account")
class AccountController extends BaseController {
	
	@Resource
	private AccountService service;

	@Resource
	private AccountRoleService accountRoleService;
	
	@Resource
	private DepartmentService departmentService;

	/**
	 * 页面的首页
	 */
	@RequestMapping(value = { "/index" })
	public String index() {
		return pageView("/system/authentication/account");
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

		DetachedCriteria criteria = DetachedCriteria.forClass(Account.class);	//构造查询对象
		criteria.createAlias("person", "person");

		criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));

		String searchText = request.getParameter("search");	//输入框查询
		if(!StringUtils.isBlank(searchText)) {
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.like("name", searchText, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("person.name", searchText, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("person.enName", searchText, MatchMode.ANYWHERE));
			criteria.add(disjunction);
		}

		String departmentId = request.getParameter("departmentId");	//查询当前部门以及所有子部门
		String[] subDepartmentIds = departmentService.getAllSubObjId(departmentId);
		if(subDepartmentIds != null && !ArrayUtils.isEmpty(subDepartmentIds)) {
			criteria.add(Restrictions.in("person.departmentId", Arrays.asList(subDepartmentIds)));
		} else {
			criteria.add(Restrictions.eq("person.departmentId", ""));
		}

		//处理排序
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		if(!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			if(sort.equals("person.name")){
				service.addPYOrder(sort, order);
			} else {
				service.addOrder(sort, order);
			}
		} else {	//默认排序
			service.addPYOrder("asc", "person.name");
		}
		return new JsonResult(service.searchPage(criteria, offset, limit));
	}

	/**
	 * 获取当前登录账户
	 * @return 当前登录账户
	 */
	@RequestMapping(value = { "/currentName" })
	@ResponseBody
	public JsonResult searchCurrentName() {
		return new JsonResult(WebUtil.getInfo(WebUtil.LoginInfo.PERSON_NAME));
	}

	/**
	 * 新增账户
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult create(@RequestBody Account account) {
		service.create(account);
		return new JsonResult(JsonResult.CREATE_SUCCEED);
	}

	/**
	 * 更新账户
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult modify(@RequestBody Account account) {
		Account oldAccount = service.get(account.getId());
		oldAccount.setName(account.getName());
		service.update(oldAccount);
		return new JsonResult(JsonResult.UPDATE_SUCCEED);
	}

	/**
	 * 删除账户
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResult delete(@PathVariable("id") String id) {
		service.delete(id);
		return new JsonResult(JsonResult.DELETE_SUCCEED);
	}

	/**
	 * 修改密码
	 * @param oldPass 旧密码
	 * @param newPass 新密码
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/modifyPassword/{oldPass}/${newPass}" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult modifyPassword(@PathVariable("oldPass") String oldPass, @PathVariable("newPass") String newPass) {
		service.modifyPassword(oldPass, newPass);
		return new JsonResult(true, "修改成功！");
	}

	/**
	 * 密码重置
	 * @param accountId 账户Id
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/resetPassword/{accountId}" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult resetPassword(@PathVariable("accountId") String accountId) {
		service.resetPassword(accountId);
		return new JsonResult(true, "重置成功！");
	}
	
	/**
	 * 根据personId检验人员是否注册过登录账号
	 * @param personId	人员Id
	 * @return 检验结果
	 */
	@RequestMapping(value = { "/isRegister/{personId}" })
	@ResponseBody
	public JsonResult hasRegisted(@PathVariable("personId") String personId) {
		return new JsonResult(service.hasRegisted(personId));
	}

	/**
	 * 检验登录名是否重复
	 * @param loginName 登录名
	 * @return 检验结果
	 */
	@RequestMapping(value = { "loginNameRepeat/{loginName}" })
	@ResponseBody
	public JsonResult loginNameExist(@PathVariable("loginName") String loginName) {
		return new JsonResult(service.loginNameExist(loginName));
	}

	/**
	 * 查询账户角色
	 * @param accountId 用户Id
	 * @return 用户角色
	 */
	@RequestMapping(value = { "/{accountId}/role" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchAccountRole(@PathVariable("accountId") String accountId) {
		return new JsonResult(accountRoleService.getAccountRole(accountId));
	}

	/**
	 * 修改账户角色
	 * @param request 请求体
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/role" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult setAccountRole(HttpServletRequest request) {
		String accountId = request.getParameter("accountId");
		String roleIds = request.getParameter("roleIds");
		String[] roleIdArr = roleIds.split(",");
		accountRoleService.setAccountRole(accountId, roleIdArr);
		return new JsonResult(true, "保存成功！");
	}
}