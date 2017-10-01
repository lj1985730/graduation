package com.graduation.core.system.controller;

import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.service.BaseService;
import com.graduation.core.system.entity.SystemConfig;
import com.graduation.core.system.service.SystemConfigService;
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
 * 基础-系统参数-控制层
 * @author Liu Jun
 * @version 2016-8-17 00:15:47
 */
@Controller
@RequestMapping(value = "**/system/base/sysConfig")
class SystemConfigController extends BaseController {
	
	@Resource
	private SystemConfigService service;

    /**
     * 页面的首页
     */
    @RequestMapping(value = { "/index" })
    public String index() {
        return pageView("/system/base/sysConfig");
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

        DetachedCriteria criteria = DetachedCriteria.forClass(SystemConfig.class);//构造DetachedCriteria对象

        criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));

        String searchText = request.getParameter("search"); //输入框查询
        if(!StringUtils.isBlank(searchText)) {
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("key", searchText, MatchMode.ANYWHERE));
            criteria.add(disjunction);
        }

        String sort = request.getParameter("sort"); //处理排序
        String order = request.getParameter("order");
        if(!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            this.service.addOrder(sort, order);
        } else {	//默认排序
            this.service.addPYOrder("asc", "key");
        }
        return new JsonResult(service.searchPage(criteria, offset, limit));
    }

    /**
     * 新增配置
     * @return 操作结果
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody SystemConfig config) {
        service.create(config);
        return new JsonResult(JsonResult.CREATE_SUCCEED);
    }

    /**
     * 更新配置
     * @return 操作结果
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult modify(@RequestBody SystemConfig config) {
        service.update(config);
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