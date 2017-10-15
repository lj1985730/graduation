package com.graduation.bus.application.service;

import com.graduation.authentication.entity.Account;
import com.graduation.authentication.service.AccountService;
import com.graduation.bus.infrastructure.entity.Route;
import com.graduation.bus.infrastructure.entity.RouteStation;
import com.graduation.core.base.dto.Page;
import com.graduation.core.base.service.BaseService;
import com.graduation.web.util.TableParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 公交管理-线路-业务层
 * @author Liu Jun at 2017-10-15 19:40:17
 * @since v1.0.0
 */
@Service
@Transactional
public class RouteService extends BaseService<Route> {

    @Resource
    private AccountService accountService;

    public Page<Route> searchPage(TableParam param, Boolean... hasTotal) {

        if(param == null) {
            return null;
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(Route.class);	//构造DetachedCriteria对象
        criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));
        criteria.createAlias("category", "category", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("startStation", "startStation", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("endStation", "endStation", JoinType.LEFT_OUTER_JOIN);

        if(StringUtils.isNotBlank(param.getSearch())) {
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("name", param.getSearch(), MatchMode.ANYWHERE));
            criteria.add(disjunction);
        }

        if(StringUtils.isNotBlank(param.getSort()) && StringUtils.isNotBlank(param.getOrder())) {
            this.addOrder(param.getSort(), param.getOrder());
        } else {
            this.addOrder("name", "asc");
        }
        return this.searchPage(criteria, param.getOffset(), param.getLimit(), hasTotal);
    }

    /**
     * 查询关联数据
     * @param routeId 线路ID
     * @param param 条件参数
     * @return 关联数据
     */
    public Page<RouteStation> searchLink(String routeId, TableParam param) {

        if(param == null || StringUtils.isBlank(routeId)) {
            return null;
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(RouteStation.class);	//构造DetachedCriteria对象
        criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));
        criteria.add(Restrictions.eq("routeId", routeId));
        criteria.addOrder(Order.asc("sort"));

        // 创建分页对象
        Page<RouteStation> pager = new Page<>(param.getOffset(), param.getLimit());
        long total = dao.getCountByCriteria(criteria);
        // 执行查询
        List<RouteStation> pageData = dao.criteriaPageQuery(criteria, param.getOffset(), param.getLimit());
        // 为pager绑定数据和总数
        pager.setRows(pageData);// 单页数据
        pager.setTotal(total);// 当前条件下的总数
        return pager;
    }

    /**
     * 线路关联站点
     * @param routeId 线路ID
     * @param stationId 站点ID
     * @param sort 序号
     */
    public void link(String routeId, String stationId, Integer sort) {
        DetachedCriteria criteria = DetachedCriteria.forClass(RouteStation.class);
        criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));
        criteria.add(Restrictions.eq("routeId", routeId));
        criteria.add(Restrictions.eq("stationId", stationId));
        List<RouteStation> list = this.searchByCriteria(criteria);
        if(!list.isEmpty()) {
            throw new RuntimeException("该线路下已存在此站点！");
        }
        RouteStation routeStation = new RouteStation();
        routeStation.setRouteId(routeId);
        routeStation.setStationId(stationId);
        routeStation.setSort(sort);
        routeStation.setDeleted(false);

        Subject subject = SecurityUtils.getSubject();	//shiro主体
        String loginName = (String)subject.getPrincipal();
        Account account = accountService.searchByName(loginName);
        routeStation.setLastModifyAccountId(account.getId());
        routeStation.setLastModifyTime(LocalDateTime.now());
        this.dao.save(routeStation);
    }

    public void dislink(String linkId) {
        dao.delete(RouteStation.class, linkId);
    }

    @Override
    protected List<Map<String, String>> genTotalRow() {
        return null;
    }

    @Override
    protected void beforeCreate(Route entity) {

        DetachedCriteria criteria = DetachedCriteria.forClass(Route.class);
        criteria.add(Restrictions.eq(DELETE_PARAM, false));
        criteria.add(Restrictions.eq("name", entity.getName()));
        if(!this.searchByCriteria(criteria).isEmpty()) {
            throw new RuntimeException("该名称已存在！");
        }

        Subject subject = SecurityUtils.getSubject();	//shiro主体
        String loginName = (String)subject.getPrincipal();
        Account account = accountService.searchByName(loginName);
        entity.setLastModifyAccountId(account.getId());
    }

    @Override
    protected void beforeDelete(Route entity) {
        Subject subject = SecurityUtils.getSubject();	//shiro主体
        String loginName = (String)subject.getPrincipal();
        Account account = accountService.searchByName(loginName);
        entity.setLastModifyAccountId(account.getId());
    }

    @Override
    protected void beforeUpdate(Route entity) {

        DetachedCriteria criteria = DetachedCriteria.forClass(Route.class);
        criteria.add(Restrictions.eq(DELETE_PARAM, false));
        criteria.add(Restrictions.eq("name", entity.getName()));
        criteria.add(Restrictions.ne("id", entity.getId()));
        if(!this.searchByCriteria(criteria).isEmpty()) {
            throw new RuntimeException("该名称已存在！");
        }

        Subject subject = SecurityUtils.getSubject();	//shiro主体
        String loginName = (String)subject.getPrincipal();
        Account account = accountService.searchByName(loginName);
        entity.setLastModifyAccountId(account.getId());
    }
}
