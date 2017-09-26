package com.graduation.authentication.controller;

import com.graduation.authentication.entity.Account;
import com.graduation.authentication.service.AccountService;
import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dao.RedisDao;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.util.TokenUtil;
import com.graduation.core.base.util.WebUtil;
import com.graduation.authentication.service.LoginService;
import com.graduation.authentication.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 系统登录-控制层
 * @author   Liu Jun
 * @version 2016-8-3 00:05:52
 */
@Controller
class LoginController extends BaseController {

	@Resource
	private LoginService service;

	@Resource
	private AccountService accountService;

	@Resource
	private MenuService menuService;

	@Resource
	private RedisDao redisDao;

	@RequestMapping(value = { "/" })
	public String index() {
		return redirectPageView("/login");
	}

	/**
	 * 登录页
	 */
	@RequestMapping(value = { "/login" })
	public ModelAndView index(HttpServletRequest request) {
		//试图检索cookie中的登录信息，如果存在，自动赋值登陆页的账户密码内容
		Account account = new Account();
		if(TokenUtil.tokenExist(request)) {
			String loginName = WebUtil.getAccountName();
			if(!StringUtils.isBlank(loginName)) {
				account.setName(loginName);
			}
		}
		return pageView("/login", "account", account);
	}

	/**
	 * 登录页
	 */
	@RequestMapping(value = { "/loginBack" })
	public String backIndex() {
		return pageView("/loginBack");
	}

	/**
	 * 前台登录动作
	 * @param userMap	登录请求信息
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult doLogin(@RequestBody Map<String, String> userMap, HttpServletRequest request, HttpServletResponse response) {

		//login step 1: load user.
		Account account = accountService.valid(userMap.get("name"), userMap.get("pass"));

		//login step 2: valid user.
		service.validLoginAuth(account);

		//login step 3: book login info.
		service.bookLoginInfo(account);	//写入用户信息、权限信息

		//login step 4: generate token.
		String token = TokenUtil.setClientToken(request, response);	//生成token，并写入request和response

		//login step 7: save login info.
//		String rememberMe = userMap.get("rememberMe");	//是否勾选了“记住密码”
//		Boolean remember = StringUtils.isEmpty(rememberMe) ? false : Boolean.valueOf(rememberMe);
//		if(remember) {	//选择了记住密码，时限默认7天
//			redisDao.setHashAll(token, map, TokenUtil.EXPIRE_SECOND);
//		} else {	//否则不设置过期时间
//			redisDao.setHashAll(token, map);
//		}

		account.setLastLogon(account.getLastModifyTime());
		accountService.update(account);

		return new JsonResult(true, "登录成功！").setData(token);
	}

	/**
	 * 登录动作
	 * @param loginMap 提交表单数据集合
	 * @return 登录结果
	 */
	@RequestMapping(value = { "/loginBack" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult doLoginBack(@RequestBody Map<String,String> loginMap) {
		String name = HtmlUtils.htmlEscape(loginMap.get("name"));
		if(StringUtils.isBlank(name)) {
			return new JsonResult(false, "用户名不能为空！");
		}
		String pass = HtmlUtils.htmlEscape(loginMap.get("pass"));
		if(StringUtils.isBlank(pass)) {
			return new JsonResult(false, "密码不能为空！");
		}

		//验证用户身份
		Account account = accountService.valid(name, pass);

		if (account == null) {
			return new JsonResult(false, "用户名或密码错误！");
		}

		if (!account.getSuperAccount()) {
			return new JsonResult(false, "非超管禁止后台登录！");
		}

		service.validLoginAuth(account);	//登录权限验证

		service.bookBackLoginInfo(account);		//写入用户信息

		return new JsonResult(true, "登录成功！");
	}

	/**
	 * 主页
	 */
	@RequestMapping(value = { "/home" })
	public String home() {
		return pageView("/index");
	}

	/**
	 * 退出系统
	 * @return 重定向登录页
	 */
	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {

        request.getSession().invalidate();	//清除session

		return redirectPageView("/login");
	}

	@RequestMapping(value = { "/top" })
	public String top() {
		return pageView("/top");
	}

	@RequestMapping(value = { "/left" })
	public String left() {
		return pageView("/left");
	}

	@RequestMapping(value = { "/bottom" })
	public String bottom() {
		return pageView("/bottom");
	}

	@RequestMapping(value = { "/error" })
	public String error() {
		return pageView("/error");
	}

}