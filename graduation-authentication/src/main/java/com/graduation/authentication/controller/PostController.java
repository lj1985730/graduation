package com.graduation.authentication.controller;

import com.graduation.authentication.entity.Post;
import com.graduation.authentication.service.PostService;
import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 权限-岗位-控制层
 * @author Liu Jun
 * @version 2016-8-14 23:29:36
 */
@Controller
@RequestMapping(value = "**/system/authentication/post")
class PostController extends BaseController {
	
	@Resource
	private PostService service;

    /**
     * 页面的首页
     */
    @RequestMapping(value = { "/index" })
    public String index() {
        return pageView("/system/authentication/post");
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

        DetachedCriteria criteria = DetachedCriteria.forClass(Post.class);//构造DetachedCriteria对象

        String departmentId = request.getParameter("departmentId"); // 构建条件采样
        criteria.add(Restrictions.eq("departmentId", departmentId));

        criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));

        String searchText = request.getParameter("search"); //输入框查询
        if(!StringUtils.isBlank(searchText)) {
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("name", searchText, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("code", searchText, MatchMode.ANYWHERE));
            criteria.add(disjunction);
        }

        //高级查询
        String code = request.getParameter("code");
        if (!StringUtils.isBlank(code)) {
            criteria.add(Restrictions.like("postCode", code, MatchMode.ANYWHERE));
        }
        String name = request.getParameter("name");
        if (!StringUtils.isBlank(name)) {
            criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }

        String sort = request.getParameter("sort"); //处理排序
        String order = request.getParameter("order");
        if(!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            if(sort.equals("name")){
                service.addPYOrder(sort, order);
            } else {
                service.addOrder(sort, order);
            }
        } else {	//默认排序
            service.addPYOrder("asc", "name");
        }
        return new JsonResult(service.searchPage(criteria, offset, limit));
    }

    /**
     * 新增岗位
     * @return 操作结果
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody Post post) {
        service.create(post);
        return new JsonResult(JsonResult.CREATE_SUCCEED);
    }

    /**
     * 更新账户
     * @return 操作结果
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult modify(@RequestBody Post post) {
        service.update(post);
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
}