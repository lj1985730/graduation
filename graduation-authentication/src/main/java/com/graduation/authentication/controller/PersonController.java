package com.graduation.authentication.controller;

import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.service.BaseService;
import com.graduation.authentication.entity.Person;
import com.graduation.authentication.service.PersonPostService;
import com.graduation.authentication.service.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限-人员-控制层
 * @author Liu Jun
 * @version 2016-8-16 22:13:53
 */
@Controller
@RequestMapping(value = "**/system/authentication/person")
class PersonController extends BaseController {
	
	@Resource
	private PersonService service;

	@Resource
	private PersonPostService personPostService;

	/**
	 * 页面的首页
	 */
	@RequestMapping(value = { "/index" })
	public String index() {
		return pageView("/system/authentication/person");
	}

	/**
	 * 查询分页列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/" })
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		int offset = Integer.parseInt(request.getParameter("offset"));	// 获取数据
		int limit = Integer.parseInt(request.getParameter("limit"));

		DetachedCriteria criteria = DetachedCriteria.forClass(Person.class);// 构造DetachedCriteria对象

		criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));	// 构建条件采样

		String searchText = request.getParameter("search");//输入框查询
		if(!StringUtils.isBlank(searchText)) {
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.like("name", searchText, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("enName", searchText, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("identitycard", searchText, MatchMode.ANYWHERE));
			criteria.add(disjunction);
		}

		//高级查询
		String name = request.getParameter("name");
		if (!StringUtils.isBlank(name)) {
			criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		String enName = request.getParameter("enName");
		if (!StringUtils.isBlank(enName)) {
			criteria.add(Restrictions.like("enName", enName, MatchMode.ANYWHERE));
		}
		String category = request.getParameter("category");
		if (!StringUtils.isBlank(category)) {
			criteria.add(Restrictions.eq("category", category));
		}
		String state = request.getParameter("state");
		if (!StringUtils.isBlank(state)) {
			criteria.add(Restrictions.eq("state", state));
		}
		String gender = request.getParameter("gender");
		if (!StringUtils.isBlank(gender)) {
			criteria.add(Restrictions.eq("gender", gender));
		}
		//处理排序
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		if(!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			if("name".equals(sort)){
				service.addPYOrder(sort, order);
			}else{
				service.addOrder(sort, order);
			}
		} else {	//默认排序
			service.addPYOrder("asc", "name");
		}
		return new JsonResult(service.searchPage(criteria, offset, limit));
	}

	/**
	 * 新增数据
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult create(@RequestBody Person person) {
		service.create(person);
		return new JsonResult(JsonResult.CREATE_SUCCEED);
	}

	/**
	 * 更新数据
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult modify(@RequestBody Person person) {
		service.update(person);
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
	 * 根据人员查询岗位
	 * @param personId 人员Id
	 * @return 角色菜单
	 */
	@RequestMapping(value = { "/{personId}/post" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPersonPost(@PathVariable("personId") String personId) {
		return new JsonResult(personPostService.getPersonPost(personId));
	}

	/**
	 * 设置角色菜单
	 * @param request 请求体
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/post" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult setPersonPost(HttpServletRequest request) {
		String personId = request.getParameter("personId");
		String postIds = request.getParameter("postIds");
		String[] postIdArr = postIds.split(",");
		personPostService.setPersonPost(personId, postIdArr);
		return new JsonResult(true, "保存成功！");
	}
}