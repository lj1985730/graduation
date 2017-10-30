package com.graduation.authentication.controller;

import com.graduation.authentication.entity.Account;
import com.graduation.authentication.service.AccountService;
import com.graduation.authentication.service.LoginService;
import com.graduation.authentication.service.MenuService;
import com.graduation.core.base.controller.BaseController;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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

	@RequestMapping(value = { "/" })
	public String index() {
		return "redirect:/home";
	}

	/**
	 * 登录页
	 */
	@RequestMapping(value = { "/login" })
	public ModelAndView loginView() {
		Account account = new Account();
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
	public JsonResult doLogin(@RequestBody Map<String, String> userMap) {

		Subject subject = SecurityUtils.getSubject();	//shiro主体
		UsernamePasswordToken token = new UsernamePasswordToken(userMap.get("name"), userMap.get("pass"));
		try {
			subject.login(token);
			subject.getSession().setAttribute("userName", userMap.get("name"));
		} catch (IncorrectCredentialsException ice) {
			throw new BusinessException("密码错误！", ice);
		}

//		//login step 1: load user.
//		Account account = accountService.valid(userMap.searchById("name"), userMap.searchById("pass"));
//
//		//login step 2: valid user.
//		service.validLoginAuth(account);
//
//		//login step 3: book login info.
//		service.bookLoginInfo(account);	//写入用户信息、权限信息
//
//		account.setLastLogon(account.getLastModifyTime());
//		accountService.update(account);
//
		return new JsonResult(true, "登录成功！");
//		return "forward:/bus/station/home";
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
		return pageView("/home");
	}

	/**
	 * 退出系统
	 * @return 重定向登录页
	 */
	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public String logout() {
		Subject subject = SecurityUtils.getSubject();	//shiro主体
		subject.logout();
		return "redirect:/login";
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