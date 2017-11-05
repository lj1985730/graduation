package com.graduation.authentication.util;

import com.graduation.authentication.entity.Account;
import com.graduation.core.base.dao.BaseDao;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.graduation.core.base.service.BaseService.DELETE_PARAM;

@Transactional
public class AuthenticationRealm extends AuthenticatingRealm {

    @Resource
    private BaseDao dao;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String loginName = (String)authenticationToken.getPrincipal();

        Session session = dao.getSessionFactory().openSession();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Account.class);
        detachedCriteria.add(Restrictions.eq("name", loginName));
        detachedCriteria.add(Restrictions.eq(DELETE_PARAM, false));
        Criteria criteria = detachedCriteria.getExecutableCriteria(session);
        criteria.setFirstResult(0).setMaxResults(1);
        List<Account> list = (List<Account>)criteria.list();
        session.close();
        if(list == null || list.isEmpty()) {
            throw new UnknownAccountException("用户不存在！");
        }
        return new SimpleAuthenticationInfo(loginName, list.get(0).getPassword(), ByteSource.Util.bytes(AuthenticationUtils.PASS_SALT), getName());
    }
}
