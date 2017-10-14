package com.graduation.bus.application.service;

import com.graduation.authentication.entity.Account;
import com.graduation.authentication.service.AccountService;
import com.graduation.bus.infrastructure.entity.Station;
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
 * 公交管理-站点-业务层
 * @author Liu Jun at 2017-10-13 22:30:37
 * @since v1.0.0
 */
@Service
@Transactional
public class StationService extends BaseService<Station> {

    @Resource
    private AccountService accountService;

    public Page<Station> searchPage(TableParam param, Boolean... hasTotal) {

        if(param == null) {
            return null;
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(Station.class);	//构造DetachedCriteria对象
        criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));

        if(StringUtils.isNotBlank(param.getSearch())) {
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("name", param.getSearch(), MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("code", param.getSearch(), MatchMode.ANYWHERE));
            criteria.add(disjunction);
        }

        if(StringUtils.isNotBlank(param.getSort()) && StringUtils.isNotBlank(param.getOrder())) {
            this.addOrder(param.getSort(), param.getOrder());
        } else {
            this.addOrder("code", "asc");
        }
        return this.searchPage(criteria, param.getOffset(), param.getLimit(), hasTotal);
    }

    @Override
    protected List<Map<String, String>> genTotalRow() {
        return null;
    }

    @Override
    protected void beforeCreate(Station entity) {
        Subject subject = SecurityUtils.getSubject();	//shiro主体
        String loginName = (String)subject.getPrincipal();
        Account account = accountService.searchByName(loginName);
        entity.setLastModifyAccountId(account.getId());
    }

    @Override
    protected void beforeDelete(Station entity) {
        Subject subject = SecurityUtils.getSubject();	//shiro主体
        String loginName = (String)subject.getPrincipal();
        Account account = accountService.searchByName(loginName);
        entity.setLastModifyAccountId(account.getId());
    }

    @Override
    protected void beforeUpdate(Station entity) {
        Subject subject = SecurityUtils.getSubject();	//shiro主体
        String loginName = (String)subject.getPrincipal();
        Account account = accountService.searchByName(loginName);
        entity.setLastModifyAccountId(account.getId());
    }
}
