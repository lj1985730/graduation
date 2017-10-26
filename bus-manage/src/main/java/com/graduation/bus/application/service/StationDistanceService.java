package com.graduation.bus.application.service;

import com.graduation.authentication.entity.Account;
import com.graduation.authentication.service.AccountService;
import com.graduation.bus.infrastructure.entity.StationDistance;
import com.graduation.core.base.dto.Page;
import com.graduation.core.base.service.BaseService;
import com.graduation.web.util.TableParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 公交管理-站点距离-业务层
 * @author Liu Jun at 2017-10-26 21:00:03
 * @since v1.0.0
 */
@Service
@Transactional
public class StationDistanceService extends BaseService<StationDistance> {

    @Resource
    private AccountService accountService;

    public Page<StationDistance> searchPage(TableParam param, Boolean... hasTotal) {

        if(param == null) {
            return null;
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(StationDistance.class);	//构造DetachedCriteria对象
        criteria.createAlias("stationA", "stationA");
        criteria.createAlias("stationB", "stationB");
        criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));

        if(StringUtils.isNotBlank(param.getSearch())) {
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("stationA.name", param.getSearch(), MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("stationB.name", param.getSearch(), MatchMode.ANYWHERE));
            criteria.add(disjunction);
        }

        if(StringUtils.isNotBlank(param.getSort()) && StringUtils.isNotBlank(param.getOrder())) {
            this.addOrder(param.getSort(), param.getOrder());
        } else {
            this.addOrder("stationA.code", "asc");
            this.addOrder("stationB.code", "asc");
        }
        return this.searchPage(criteria, param.getOffset(), param.getLimit(), hasTotal);
    }

    @Override
    protected List<Map<String, String>> genTotalRow() {
        return null;
    }

    @Override
    protected void beforeCreate(StationDistance entity) {
        Subject subject = SecurityUtils.getSubject();	//shiro主体
        String loginName = (String)subject.getPrincipal();
        Account account = accountService.searchByName(loginName);
        entity.setLastModifyAccountId(account.getId());
    }

    @Override
    protected void beforeDelete(StationDistance entity) {
        Subject subject = SecurityUtils.getSubject();	//shiro主体
        String loginName = (String)subject.getPrincipal();
        Account account = accountService.searchByName(loginName);
        entity.setLastModifyAccountId(account.getId());
    }

    @Override
    protected void beforeUpdate(StationDistance entity) {
        Subject subject = SecurityUtils.getSubject();	//shiro主体
        String loginName = (String)subject.getPrincipal();
        Account account = accountService.searchByName(loginName);
        entity.setLastModifyAccountId(account.getId());
    }
}
