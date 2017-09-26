package com.graduation.authentication.controller;

import com.graduation.authentication.entity.EducationExperience;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.service.BaseService;
import com.graduation.authentication.service.EducationExperienceService;
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
 * 权限-教育经历-控制层
 * @author Liu Jun
 * @version 2016-8-16 22:49:56
 */
@Controller
@RequestMapping(value = "**/system/authentication/educationExperience")
class EducationExperienceController {

    @Resource
    private EducationExperienceService service;

    /**
     * 查询人员对应 教育经历分页 列表
     * @return 教育经历分页
     */
    @RequestMapping(value = { "/" })
    @ResponseBody
    public JsonResult searchPage(HttpServletRequest request) {
        int offset = Integer.parseInt(request.getParameter("offset"));// 获取数据
        int limit = Integer.parseInt(request.getParameter("limit"));
        // 构造DetachedCriteria对象
        DetachedCriteria criteria = DetachedCriteria.forClass(EducationExperience.class);

        criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));	// 构建条件采样

        String personId = request.getParameter("personId");	//人员过滤
        criteria.add(Restrictions.eq("personId", personId));

        //输入框查询
        String searchText = request.getParameter("search");
        if(!StringUtils.isBlank(searchText)) {
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("institution", searchText, MatchMode.ANYWHERE));
            criteria.add(disjunction);
        }
        service.addOrder("asc", "startDate");
        return new JsonResult(service.searchPage(criteria, offset, limit));
    }

    /**
     * 新增教育经历
     * @param experience 教育经历
     * @return 操作结果
     */
    @RequestMapping(value = { "/personEducation/" }, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody EducationExperience experience) {
        service.create(experience);
        return new JsonResult(JsonResult.CREATE_SUCCEED);
    }

    /**
     * 更新教育经历
     * @param experience 教育经历
     * @return 操作结果
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult modify(@RequestBody EducationExperience experience) {
        service.update(experience);
        return new JsonResult(JsonResult.UPDATE_SUCCEED);
    }

    /**
     * 删除教育经历
     * @param id 教育经历Id
     * @return 操作结果
     */
    @RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult delete(@PathVariable("id") String id) {
        service.delete(id);
        return new JsonResult(JsonResult.DELETE_SUCCEED);
    }
}
