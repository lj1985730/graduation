package com.graduation.authentication.util;

import com.graduation.authentication.entity.Menu;
import com.graduation.core.base.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 系统-帮助类-WEB帮助类
 * @author liu jun
 * @version 2016-7-31 15:23:42
 */
public class AuthenticationUtil {

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
     * 获取登录信息-账户ID
     * @return 账户ID
     */
    public static String getAccountId() {
        Object object = getInfo(LoginInfo.ACCOUNT_ID);
        return object == null ? null : object.toString();
    }

    /**
     * 获取登录信息-账户密码
     * @return 账户密码
     */
    public static String getPass() {
        Object object = getInfo(LoginInfo.ACCOUNT_PASS);
        return object == null ? null : object.toString();
    }

    /**
     * 获取登录信息-账户名称
     * @return 账户名称
     */
    public static String getAccountName() {
        Object object = getInfo(LoginInfo.ACCOUNT_NAME);
        return object == null ? null : object.toString();
    }

    /**
     * 获取登录信息-是否超管
     * @return 是否超管
     */
    public static Boolean isSuperAccount() {
        Object object = getInfo(LoginInfo.SUPER_ACCOUNT);
        return object != null && StringUtils.isNotBlank(getInfo(LoginInfo.SUPER_ACCOUNT).toString()) && (getInfo(LoginInfo.SUPER_ACCOUNT).toString()).equals("1");
    }

    /**
     * 获取登录信息-部门ID
     * @return 部门ID
     */
    public static String getDepartmentId() {
        Object object = getInfo(LoginInfo.DEPARTMENT_ID);
        return object == null ? null : object.toString();
    }

    /**
     * 获取登录信息-部门名称
     * @return 部门名称
     */
    public static String getDepartmentName() {
        Object object = getInfo(LoginInfo.DEPARTMENT_NAME);
        return object == null ? null : object.toString();
    }

    /**
     * 获取登录信息-岗位ID,多个用","分隔
     * @return 岗位ID,多个用","分隔
     */
    public static String getPostId() {
        Object object = getInfo(LoginInfo.POST_ID);
        return object == null ? null : object.toString();
    }

    /**
     * 获取登录信息-岗位ID数组
     * @return 岗位ID数组
     */
    public static String[] getPostIdArr() {
        Object object = getInfo(LoginInfo.POST_ID_ARR);
        return object == null ? null : (String[])object;
    }

    /**
     * 获取登录信息-岗位名称,多个用","分隔
     * @return 岗位名称,多个用","分隔
     */
    public static String getPostName() {
        Object object = getInfo(LoginInfo.POST_NAME);
        return object == null ? null : object.toString();
    }

    /**
     * 获取登录信息-岗位名称数组
     * @return 岗位名称数组
     */
    public static String[] getPostNameArr() {
        Object object = getInfo(LoginInfo.POST_NAME_ARR);
        return object == null ? null : (String[])object;
    }

    /**
     * 获取登录信息-角色ID,多个用","分隔
     * @return 角色ID,多个用","分隔
     */
    public static String getRoleId() {
        Object object = getInfo(LoginInfo.ROLE_ID);
        return object == null ? null : object.toString();
    }

    /**
     * 获取登录信息-角色ID数组
     * @return 角色ID数组
     */
    public static String[] getRoleIdArr() {
        Object object = getInfo(LoginInfo.ROLE_ID_ARR);
        return object == null ? null : (String[])object;
    }

    /**
     * 获取登录信息-角色名称,多个用","分隔
     * @return 角色名称,多个用","分隔
     */
    public static String getRoleName() {
        Object object = getInfo(LoginInfo.ROLE_NAME);
        return object == null ? null : object.toString();
    }

    /**
     * 获取登录信息-角色名称数组
     * @return 角色名称数组
     */
    public static String[] getRoleNameArr() {
        Object object = getInfo(LoginInfo.ROLE_NAME_ARR);
        return object == null ? null : (String[])object;
    }

    /**
     * 获取登录信息-人员ID
     * @return 人员ID
     */
    public static String getPersonId() {
        Object object = getInfo(LoginInfo.PERSON_ID);
        return object == null ? null : object.toString();
    }

    /**
     * 获取登录信息-人员名称
     * @return 人员名称
     */
    public static String getPersonName() {
        Object object = getInfo(LoginInfo.PERSON_NAME);
        return object == null ? null : object.toString();
    }

    /**
     * 获取登录信息-人员邮箱地址
     * @return 人员邮箱地址
     */
    public static String getPersonEmail() {
        Object object = getInfo(LoginInfo.PERSON_EMAIL);
        return object == null ? null : object.toString();
    }

    /**
     * 获取权限菜单信息ID,多个用","分隔
     * @return 权限菜单
     */
    public static String getMenuId() {
        Object object = getInfo(LoginInfo.MENU_ID);
        return object == null ? null : object.toString();
    }

    /**
     * 获取权限菜单信息ID数组
     * @return 权限菜单信息ID数组
     */
    public static String[] getMenuIdArr() {
        Object object = getInfo(LoginInfo.MENU_ID_ARR);
        return object == null ? null : (String[])object;
    }

    /**
     * 获取权限菜单信息队列
     * @return 权限菜单信息队列
     */
    public static List<Menu> getMenuList() {
        Object object = getInfo(LoginInfo.MENU_LIST);
        return object == null ? null : (List<Menu>)object;
    }
}
