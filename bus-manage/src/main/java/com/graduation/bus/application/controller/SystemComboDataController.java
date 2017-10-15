package com.graduation.bus.application.controller;

import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.system.entity.SystemComboData;
import com.graduation.bus.application.service.SystemComboDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 基础-系统下拉框-控制层
 * @author Liu Jun at 2017-10-15 20:57:08
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/system/base/sysComboData")
class SystemComboDataController extends BaseController {

    @Resource
    private SystemComboDataService service;

    /**
     * 页面的首页
     */
    @RequestMapping(value = { "/index" })
    public String index() {
        return pageView("/system/base/sysComboData");
    }

    /**
     * 查询分页列表
     * @return 分页数据
     */
    @RequestMapping(value = { "/{key}" })
    @ResponseBody
    public JsonResult searchData(@PathVariable String key) {
        return new JsonResult(service.loadComboSql(key));
    }

    /**
     * 新增配置
     * @return 操作结果
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody SystemComboData entity) {
        service.create(entity);
        return new JsonResult(JsonResult.CREATE_SUCCEED);
    }

    /**
     * 更新配置
     * @return 操作结果
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult modify(@RequestBody SystemComboData entity) {
        service.update(entity);
        return new JsonResult(JsonResult.UPDATE_SUCCEED);
    }

    /**
     * 删除配置
     * @return 操作结果
     */
    @RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult delete(@PathVariable("id") String id) {
        service.delete(id);
        return new JsonResult(JsonResult.DELETE_SUCCEED);
    }
}