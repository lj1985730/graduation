package com.graduation.bus.application.controller;

import com.graduation.bus.application.service.StationDistanceService;
import com.graduation.bus.application.service.StationService;
import com.graduation.bus.infrastructure.entity.Station;
import com.graduation.bus.infrastructure.entity.StationDistance;
import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.web.util.TableParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 公交管理-站点距离-控制层
 * @author Liu Jun at 2017-10-26 21:03:31
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "/bus")
class StationDistanceController extends BaseController {
	
	@Resource
	private StationDistanceService service;

	/**
	 * 页面的首页
	 */
	@RequestMapping(value = { "/stationDistance/home" })
	public String home() {
		return pageView("/bus/stationDistance");
	}

	/**
	 * 查询分页列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/stationDistances" })
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		return new JsonResult(service.searchPage(new TableParam(request)));
    }

	/**
	 * 新增数据
	 * @return 操作结果
	 */
    @RequestMapping(value = { "/stationDistance" }, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody StationDistance entity) {
        service.create(entity);
		return new JsonResult(true, JsonResult.CREATE_SUCCEED);
    }

	/**
	 * 更新数据
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/stationDistance" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult modify(@RequestBody StationDistance entity) {
		service.update(entity);
		return new JsonResult(true, JsonResult.UPDATE_SUCCEED);
	}

	/**
	 * 删除数据
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/stationDistance/{id}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResult delete(@PathVariable("id") String id) {
		service.delete(id);
		return new JsonResult(true, JsonResult.DELETE_SUCCEED);
	}
}