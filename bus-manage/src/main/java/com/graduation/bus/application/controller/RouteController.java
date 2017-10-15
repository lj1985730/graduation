package com.graduation.bus.application.controller;

import com.graduation.bus.application.service.RouteService;
import com.graduation.bus.infrastructure.entity.Route;
import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.web.util.TableParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 公交管理-线路-控制层
 * @author Liu Jun at 2017-10-15 19:28:34
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "/bus")
class RouteController extends BaseController {

	@Resource
	private RouteService service;

	/**
	 * 页面的首页
	 */
	@RequestMapping(value = { "/route/home" })
	public String home() {
		return pageView("/bus/route");
	}

	/**
	 * 页面的首页
	 */
	@RequestMapping(value = { "/route/readonly" })
	public String readonly() {
		return pageView("/bus/route-readonly");
	}

	/**
	 * 查询分页列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/routes" })
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		return new JsonResult(service.searchPage(new TableParam(request)));
    }

	/**
	 * 新增数据
	 * @return 操作结果
	 */
    @RequestMapping(value = { "/route" }, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody Route entity) {
        service.create(entity);
		return new JsonResult(true, JsonResult.CREATE_SUCCEED);
    }

	/**
	 * 更新数据
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/route" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult modify(@RequestBody Route entity) {
		service.update(entity);
		return new JsonResult(true, JsonResult.UPDATE_SUCCEED);
	}

	/**
	 * 删除数据
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/route/{id}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResult delete(@PathVariable("id") String id) {
		service.delete(id);
		return new JsonResult(true, JsonResult.DELETE_SUCCEED);
	}

	/**
	 * 查询线路关联站点
	 * @return 线路关联站点
	 * @param routeId 线路ID
	 */
	@RequestMapping(value = { "/route/{routeId}/stations" })
	@ResponseBody
	public JsonResult searchLink(@PathVariable String routeId, HttpServletRequest request) {
		return new JsonResult(service.searchLink(routeId, new TableParam(request)));
	}

	/**
	 * 线路关联站点
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/route/{routeId}/station/{stationId}/{sort}" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult link(@PathVariable String routeId, @PathVariable String stationId, @PathVariable Integer sort) {
		service.link(routeId, stationId, sort);
		return new JsonResult(true, JsonResult.CREATE_SUCCEED);
	}

	/**
	 * 删除线路关联站点
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/route/station/{linkId}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResult link(@PathVariable String linkId) {
		service.dislink(linkId);
		return new JsonResult(true, JsonResult.CREATE_SUCCEED);
	}
}