package com.graduation.authentication.util;

import com.graduation.authentication.entity.Account;
import com.graduation.authentication.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class AuthenticationRealm extends AuthenticatingRealm {

    @Resource
    private AccountService accountService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String loginName = (String)authenticationToken.getPrincipal();
        Account account = accountService.searchByName(loginName);
        if(account == null) {
            throw new UnknownAccountException("用户不存在！");
        }

        return new SimpleAuthenticationInfo(loginName, account.getPassword(), ByteSource.Util.bytes(AuthenticationUtils.PASS_SALT), getName());
    }
}
