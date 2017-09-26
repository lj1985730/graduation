package com.graduation.authentication.controller;

import com.graduation.authentication.entity.Button;
import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.service.BaseService;
import com.graduation.authentication.service.ButtonService;
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
 * 权限-按钮-控制层
 * @author Liu Jun
 * @version 2016-8-17 00:15:47
 */
@Controller
@RequestMapping(value = "**/system/authentication/button")
class ButtonController extends BaseController {
	
	@Resource
	private ButtonService service;

    /**
     * 页面的首页
     */
    @RequestMapping(value = { "/index" })
    public String index() {
        return pageView("/system/authentication/button");
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

        DetachedCriteria criteria = DetachedCriteria.forClass(Button.class);//构造DetachedCriteria对象

        String menuId = request.getParameter("menuId"); // 构建条件采样
        criteria.add(Restrictions.eq("menuId", menuId));

        criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));

        String searchText = request.getParameter("search"); //输入框查询
        if(!StringUtils.isBlank(searchText)) {
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("name", searchText, MatchMode.ANYWHERE));
            criteria.add(disjunction);
        }

        String sort = request.getParameter("sort"); //处理排序
        String order = request.getParameter("order");
        if(!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            if(sort.equals("name")){
                this.service.addPYOrder(sort, order);
            } else {
                this.service.addOrder(sort, order);
            }
        } else {	//默认排序
            this.service.addPYOrder("asc", "name");
        }
        return new JsonResult(service.searchPage(criteria, offset, limit));
    }

    /**
     * 新增按钮
     * @return 操作结果
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody Button button) {
        service.create(button);
        return new JsonResult(JsonResult.CREATE_SUCCEED);
    }

    /**
     * 更新按钮
     * @return 操作结果
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult modify(@RequestBody Button button) {
        service.update(button);
        return new JsonResult(JsonResult.UPDATE_SUCCEED);
    }

    /**
     * 删除按钮
     * @return 操作结果
     */
    @RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult delete(@PathVariable("id") String id) {
        service.delete(id);
        return new JsonResult(JsonResult.DELETE_SUCCEED);
    }
}