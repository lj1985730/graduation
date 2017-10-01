package com.graduation.authentication.controller;


import com.graduation.authentication.entity.Department;
import com.graduation.authentication.service.DepartmentService;
import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.service.BaseService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "**/authentication/department")
class DepartmentController extends BaseController {

	@Resource
	private DepartmentService service;

	/**
	 * 页面的首页
	 */
	@RequestMapping(value = { "/homeView" })
	public String home() {
		return pageView("/authentication/department");
	}
	
	/**
	 * 查询机构树
	 * @param enabled 是否启用
	 * @return 机构树
	 */
	@RequestMapping(value = { "/tree/{enabled}" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchTree(@PathVariable("enabled") Boolean enabled) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Department.class);	// 构造DetachedCriteria对象
		criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));
		if (enabled){
			criteria.add(Restrictions.eq("enabled", true));
		}
		List<Department> list = service.searchByCriteria(criteria);

		Department department = new Department();
		department.setId(null);
		department.setName("总部");
		department.setParentId("#");
		department.setSort(0);
		department.setLevel(0);
		list.add(department);
		return new JsonResult(list);
	}
	
	/**
	 * 按id查询
	 * @param id 主键
	 * @return 部门
	 */
	@RequestMapping(value = { "/{id}" })
	@ResponseBody
	public JsonResult search(@PathVariable("id") String id) {
		return new JsonResult(service.get(id));
	}

	/**
	 * 新增部门
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult create(@RequestBody Department department) {
		service.create(department);
		return new JsonResult(JsonResult.CREATE_SUCCEED);
	}

	/**
	 * 更新部门
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult modify(@RequestBody Department department) {
		service.update(department);
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
	 * 部门启用/停用
	 * @param departmentId 部门Id
	 * @param enable true 启用；false 停用；
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/triggerEnable/{departmentId}/{enable}" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult triggerEnable(@PathVariable("departmentId") String departmentId, @PathVariable("enable") Boolean enable) {
		service.triggerEnable(departmentId, enable);
		return new JsonResult(true, JsonResult.UPDATE_SUCCEED);
	}

	/**
	 * 取当前节点同级的最大排序
	 * @param parentId 父节点Id
	 * @return 最大排序号
	 */
	@RequestMapping(value = { "/maxSort/{parentId}/" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult searchMaxSort(@PathVariable("parentId") String parentId) {
		return new JsonResult(service.searchMaxSort(parentId));
	}
}