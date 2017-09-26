package com.graduation.core.base.util;

import com.graduation.core.base.exception.BusinessException;
import com.graduation.core.base.dao.RedisDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Map;

import static com.graduation.core.base.util.WebUtil.LoginInfo.*;

/**
 * 系统-帮助类-WEB帮助类
 * @author liu jun
 * @version 2016-7-31 15:23:42
 */
public class WebUtil {

    public enum LoginInfo {
        ACCOUNT_ID, ACCOUNT_NAME, ACCOUNT_PASS, SUPER_ACCOUNT,
        PERSON_ID, PERSON_NAME, PERSON_EMAIL,
        DEPARTMENT_ID, DEPARTMENT_NAME,
        ROLE_ID, ROLE_NAME, ROLE_ID_ARR, ROLE_NAME_ARR,
        POST_ID, POST_NAME, POST_ID_ARR, POST_NAME_ARR,
//        BUTTON_ID, BUTTON_ID_ARR,
        MENU_ID, MENU_ID_ARR, MENU_LIST
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static HttpServletRequest getRequest() throws BusinessException {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attrs.getRequest();
        } catch (Exception e) {
            throw new BusinessException("获取RequestContext失败！");
        }
    }

    /**
     * 设置登录信息
     * @param key   登录信息键，枚举
     * @param value 登录信息值
     */
    public static void setInfo(LoginInfo key, Object value) {
        getSession().setAttribute(key.toString(), value);
    }

    /**
     * 获取登录信息
     * @param key   登录信息键，枚举
     */
    public static Object getInfo(LoginInfo key) {
        return getSession().getAttribute(key.toString());
    }

    /**
     * 获取服务端的全部登录信息
     * @return 登录信息值
     */
    protected static Map<Serializable, Object> getAllLoginInfo() {
        String token = TokenUtil.getClientToken();
        RedisDao dao = SpringContextUtil.getBean("redisDao", RedisDao.class);
        return dao.getHashMap(token);
    }

    /**
     * 获取服务端的登录信息
     * @param key 信息键
     * @return 登录信息值
     */
    protected static Object getLoginInfo(LoginInfo key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = TokenUtil.getClientToken(request);
        return getLoginInfo(token, key);
    }

    /**
     * 获取服务端的登录信息
     * @param token 登录验证token
     * @param key 信息键
     * @return 登录信息值
     */
    protected static Object getLoginInfo(String token, LoginInfo key) {
        if(StringUtils.isBlank(token)) {
            throw new BusinessException("身份认证失败，请重新登录！");
        }
        RedisDao dao = SpringContextUtil.getBean("redisDao", RedisDao.class);
        return dao.getValue(token, key);
    }

    /**
     * 获取登录信息-账户ID
     * @return 账户ID
     */
    public static String getAccountId() {
        return (String)getLoginInfo(ACCOUNT_ID);
    }

    /**
     * 获取登录信息-账户ID
     * @param token 登录验证token
     * @return 账户ID
     */
    public static String getAccountId(String token) {
        return (String)getLoginInfo(token, ACCOUNT_ID);
    }

    /**
     * 获取登录信息-账户密码
     * @return 账户密码
     */
    public static String getPass() {
        return (String)getLoginInfo(ACCOUNT_PASS);
    }

    /**
     * 获取登录信息-账户密码
     * @param token 登录验证token
     * @return 账户密码
     */
    public static String getPass(String token) {
        return (String)getLoginInfo(token, ACCOUNT_PASS);
    }

    /**
     * 获取登录信息-账户名称
     * @return 账户名称
     */
    public static String getAccountName() {
        return (String)getLoginInfo(ACCOUNT_NAME);
    }

    /**
     * 获取登录信息-账户名称
     * @param token 登录验证token
     * @return 账户名称
     */
    public static String getAccountName(String token) {
        return (String)getLoginInfo(token, ACCOUNT_NAME);
    }

    /**
     * 获取登录信息-是否超管
     * @return 是否超管
     */
    public static Boolean isSuperAccount() {
        return !StringUtils.isBlank((String)getLoginInfo(SUPER_ACCOUNT)) && ((String)getLoginInfo(SUPER_ACCOUNT)).equals("1");
    }

    /**
     * 获取登录信息-是否超管
     * @param token 登录验证token
     * @return 是否超管
     */
    public static Boolean isSuperAccount(String token) {
        return !StringUtils.isBlank((String)getLoginInfo(token, SUPER_ACCOUNT)) && ((String)getLoginInfo(token, SUPER_ACCOUNT)).equals("1");
    }

    /**
     * 获取登录信息-部门ID
     * @return 部门ID
     */
    public static String getDeptId() {
        return (String)getLoginInfo(DEPARTMENT_ID);
    }

    /**
     * 获取登录信息-部门ID
     * @param token 登录验证token
     * @return 部门ID
     */
    public static String getDeptId(String token) {
        return (String)getLoginInfo(token, DEPARTMENT_ID);
    }

    /**
     * 获取登录信息-部门名称
     * @return 部门名称
     */
    public static String getDeptName() {
        return (String)getLoginInfo(DEPARTMENT_NAME);
    }

    /**
     * 获取登录信息-部门名称
     * @param token 登录验证token
     * @return 部门名称
     */
    public static String getDeptName(String token) {
        return (String)getLoginInfo(token, DEPARTMENT_NAME);
    }

    /**
     * 获取登录信息-岗位ID,多个用","分隔
     * @return 岗位ID,多个用","分隔
     */
    public static String getPostId() {
        return (String)getLoginInfo(POST_ID);
    }

    /**
     * 获取登录信息-岗位ID,多个用","分隔
     * @param token 登录验证token
     * @return 岗位ID,多个用","分隔
     */
    public static String getPostId(String token) {
        return (String)getLoginInfo(token, POST_ID);
    }

    /**
     * 获取登录信息-岗位ID数组
     * @return 岗位ID数组
     */
    public static String[] getPostIdArr() {
        return (String[])getLoginInfo(POST_ID_ARR);
    }

    /**
     * 获取登录信息-岗位ID数组
     * @param token 登录验证token
     * @return 岗位ID数组
     */
    public static String[] getPostIdArr(String token) {
        return (String[])getLoginInfo(token, POST_ID_ARR);
    }

    /**
     * 获取登录信息-岗位名称,多个用","分隔
     * @return 岗位名称,多个用","分隔
     */
    public static String getPostName() {
        return (String)getLoginInfo(POST_NAME);
    }

    /**
     * 获取登录信息-岗位名称,多个用","分隔
     * @param token 登录验证token
     * @return 岗位名称,多个用","分隔
     */
    public static String getPostName(String token) {
        return (String)getLoginInfo(token, POST_NAME);
    }

    /**
     * 获取登录信息-岗位名称数组
     * @return 岗位名称数组
     */
    public static String[] getPostNameArr() {
        return (String[])getLoginInfo(POST_NAME_ARR);
    }

    /**
     * 获取登录信息-岗位名称数组
     * @param token 登录验证token
     * @return 岗位名称数组
     */
    public static String[] getPostNameArr(String token) {
        return (String[])getLoginInfo(token, POST_NAME_ARR);
    }

    /**
     * 获取登录信息-角色ID,多个用","分隔
     * @return 角色ID,多个用","分隔
     */
    public static String getRoleId() {
        return (String)getLoginInfo(ROLE_ID);
    }

    /**
     * 获取登录信息-角色ID,多个用","分隔
     * @param token 登录验证token
     * @return 角色ID,多个用","分隔
     */
    public static String getRoleId(String token) {
        return (String)getLoginInfo(token, ROLE_ID);
    }

    /**
     * 获取登录信息-角色ID数组
     * @return 角色ID数组
     */
    public static String[] getRoleIdArr() {
        return (String[])getLoginInfo(ROLE_ID_ARR);
    }

    /**
     * 获取登录信息-角色ID数组
     * @param token 登录验证token
     * @return 角色ID数组
     */
    public static String[] getRoleIdArr(String token) {
        return (String[])getLoginInfo(token, ROLE_ID_ARR);
    }

    /**
     * 获取登录信息-角色名称,多个用","分隔
     * @return 角色名称,多个用","分隔
     */
    public static String getRoleName() {
        return (String)getLoginInfo(ROLE_NAME);
    }

    /**
     * 获取登录信息-角色名称,多个用","分隔
     * @param token 登录验证token
     * @return 角色名称,多个用","分隔
     */
    public static String getRoleName(String token) {
        return (String)getLoginInfo(token, ROLE_NAME);
    }

    /**
     * 获取登录信息-角色名称数组
     * @return 角色名称数组
     */
    public static String[] getRoleNameArr() {
        return (String[])getLoginInfo(ROLE_NAME_ARR);
    }

    /**
     * 获取登录信息-角色名称数组
     * @param token 登录验证token
     * @return 角色名称数组
     */
    public static String[] getRoleNameArr(String token) {
        return (String[])getLoginInfo(token, ROLE_NAME_ARR);
    }

    /**
     * 获取登录信息-人员ID
     * @return 人员ID
     */
    public static String getPersonId() {
        return (String)getLoginInfo(PERSON_ID);
    }

    /**
     * 获取登录信息-人员ID
     * @param token 登录验证token
     * @return 人员ID
     */
    public static String getPersonId(String token) {
        return (String)getLoginInfo(token, PERSON_ID);
    }

    /**
     * 获取登录信息-人员名称
     * @return 人员名称
     */
    public static String getPersonName() {
        return (String)getLoginInfo(PERSON_NAME);
    }

    /**
     * 获取登录信息-人员名称
     * @param token 登录验证token
     * @return 人员名称
     */
    public static String getPersonName(String token) {
        return (String)getLoginInfo(token, PERSON_NAME);
    }

    /**
     * 获取登录信息-人员邮箱地址
     * @return 人员邮箱地址
     */
    public static String getPersonEmail() {
        return (String)getLoginInfo(PERSON_EMAIL);
    }

    /**
     * 获取登录信息-人员邮箱地址
     * @param token 登录验证token
     * @return 人员邮箱地址
     */
    public static String getPersonEmail(String token) {
        return (String)getLoginInfo(token, PERSON_EMAIL);
    }

    /**
     * 获取权限菜单信息ID,多个用","分隔
     * @return 权限菜单
     */
    public static String getMenuId() {
        return (String)getLoginInfo(MENU_ID);
    }

    /**
     * 获取权限菜单信息ID,多个用","分隔
     * @param token 登录验证token
     * @return 权限菜单
     */
    public static String getMenuId(String token) {
        return (String)getLoginInfo(token, MENU_ID);
    }

    /**
     * 获取权限菜单信息ID数组
     * @return 权限菜单信息ID数组
     */
    public static String[] getMenuIdArr() {
        return (String[])getLoginInfo(MENU_ID_ARR);
    }

    /**
     * 获取权限菜单信息ID数组
     * @param token 登录验证token
     * @return 权限菜单信息ID数组
     */
    public static String[] getMenuIdArr(String token) {
        return (String[])getLoginInfo(token, MENU_ID_ARR);
    }
}
