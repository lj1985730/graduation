package com.graduation.authentication.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.graduation.authentication.entity.Menu;
import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.exception.BusinessException;
import com.graduation.core.base.service.BaseService;
import com.graduation.authentication.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限-菜单-控制层
 * @author Liu Jun
 * @version 2016-8-11 21:24:18
 */
@Controller
@RequestMapping(value = "**/authentication/menu")
class MenuController extends BaseController {

    @Resource
    MenuService service;

    /**
     * 页面的首页
     */
    @RequestMapping(value = { "/homeView" })
    public String home() {
        return pageView("/authentication/menu");
    }

    /**
     * 获取手风琴菜单
     */
    @RequestMapping(value = { "/" })
    @ResponseBody
    public JsonResult getAccordionMenu() {

        List<Menu> menuList = service.getAccountMenu();
        if(menuList == null || menuList.isEmpty()) {
            return null;
        }
        List<Menu> levelOneMenu = new ArrayList<>();

        menuList.forEach(menu -> {
            if (menu.getLevel() == 1) {
                levelOneMenu.add(menu);
            }
        });

        Writer jsonWriter = new StringWriter();
        JsonGenerator generator;
        try {
            generator = new JsonFactory().createGenerator(jsonWriter);
            generator.writeStartArray();
            for (Menu menu : levelOneMenu) {
                generator.writeStartObject();
                generator.writeStringField("id", menu.getId());
                generator.writeStringField("name", menu.getName());
                generator.writeStringField("icon", menu.getIcon());
                generator.writeBooleanField("leaf", false);
                generator.writeStringField("url", menu.getUrl());
                generator.writeFieldName("children");
                generator.writeStartArray();

                List<Menu> levelTwoMenu = new ArrayList<>();
                menuList.forEach(subMenu -> {
                    if ((subMenu.getLevel() == 2) && subMenu.getParentId().equals(menu.getId())) {
                        levelTwoMenu.add(subMenu);
                    }
                });

                for (Menu subMenu : levelTwoMenu) {
                    generator.writeStartObject();
                    generator.writeStringField("id", subMenu.getId());
                    generator.writeStringField("name", subMenu.getName());
                    generator.writeStringField("icon", subMenu.getIcon());
                    generator.writeBooleanField("leaf", true);
                    generator.writeStringField("url", subMenu.getUrl());
                    generator.writeEndObject();
                }
                generator.writeEndArray();
                generator.writeEndObject();
            }
            generator.writeEndArray();
            generator.flush();
            generator.close();
        } catch (IOException e) {
            throw new BusinessException("菜单生成失败！", e);
        }
        return new JsonResult(jsonWriter.toString());
    }

    /**
     * 查询菜单树数据源
     * @param request 请求体
     * @return 菜单树数据源
     */
    @RequestMapping(value = { "/tree" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult searchTree(HttpServletRequest request) {
        // 构造DetachedCriteria对象
        DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
        criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));

        String enabled = request.getParameter("enabled");
        if(!StringUtils.isBlank(enabled)) {
            criteria.add(Restrictions.eq("enabled", Boolean.valueOf(enabled)));
        }

        service.addOrder("sortNo", "asc");    //按排序号正序
        List<Menu> menus = service.searchByCriteria(criteria);
        return new JsonResult(menus);
    }

    /**
     * 获取用户权限下所有菜单列表
     * @return 菜单列表
     */
    @RequestMapping(value = { "/getUserMenu" }, method= RequestMethod.GET)
    @ResponseBody
    public JsonResult getUserMenu() {
        return new JsonResult(service.getAccountMenu());
    }

    /**
     * 添加
     * @param menu 待增加菜单
     * @return 操作结果
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody Menu menu) {
        service.create(menu);
        return new JsonResult(true, JsonResult.CREATE_SUCCEED);
    }

    /**
     * 修改
     * @param menu 待增加菜单
     * @return 操作结果
     */
    @ResponseBody
    @RequestMapping(value = { "/" }, method = RequestMethod.PUT)
    public JsonResult modify(@RequestBody Menu menu) {
        service.update(menu);
        return new JsonResult(true, JsonResult.UPDATE_SUCCEED);
    }

    /**
     * 删除菜单
     * @param menuId 待删除菜单Id
     * @return 操作结果
     */
    @RequestMapping(value = { "/{menuId}" }, method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult delete(@PathVariable("menuId") String menuId) {
        service.delete(menuId);
        return new JsonResult(true, JsonResult.DELETE_SUCCEED);
    }

    /**
     * 菜单启用/停用
     * @param menuId 菜单Id
     * @param enable true 启用；false 停用；
     * @return 操作结果
     */
    @RequestMapping(value = { "/triggerEnable/{menuId}/{enable}" }, method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult triggerEnable(@PathVariable("menuId") String menuId, @PathVariable("enable") Boolean enable) {
        service.triggerEnable(menuId, enable);
        return new JsonResult(true, JsonResult.UPDATE_SUCCEED);
    }
}