package com.graduation.authentication.service;

import com.graduation.authentication.entity.Account;
import com.graduation.authentication.entity.AccountRole;
import com.graduation.authentication.base.service.SystemLoader;
import com.graduation.authentication.util.AuthenticationUtil;
import com.graduation.core.base.exception.BusinessException;
import com.graduation.core.base.security.Encryptor;
import com.graduation.core.base.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 权限-账户-业务层
 * @author Liu Jun
 * @version 2016-7-31 14:55:07
 */
@Service
@Transactional
public class AccountService extends BaseService<Account> {

	private static final String defaultPassword = "123456";

	/**
	 * 登录验证
	 * @param username	用户名
	 * @param password	密码
	 * @return	登录用户对象
	 */
    public Account valid(String username, String password) {
		try {
			//用户名和密码查询登录账户
			String truePassword = Encryptor.encryptPassword(password);
			
			DetachedCriteria criteria = DetachedCriteria.forClass(Account.class);
			criteria.add(Restrictions.eq("name", username));
			criteria.add(Restrictions.eq("password", truePassword));
			criteria.add(Restrictions.eq(DELETE_PARAM, false));
			List<Account> accounts = dao.criteriaPageQuery(criteria, 0, 1);
			if (accounts.size() == 0) {
				return null;
			} else {
				return accounts.get(0);
			}
		} catch (Exception e) {
			throw new BusinessException("登录失败，账户验证出错！", e);
		}
    }

	/**
	 * 判断该用户在AccountRole表中是否有相应的roleId
	 * @param accountId	账户
	 * @param roleId	角色
	 * @return true 有；false 没有
	 */
	public Boolean hasRole(final String accountId, final String roleId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AccountRole.class);
		// 构造条件
		criteria.add(Restrictions.eq("accountId", accountId));
		criteria.add(Restrictions.eq("roleId", roleId));
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		return dao.getCountByCriteria(criteria) > 0;
	}
	
	/**
	 * 修改密码
	 * @param oldPass	提交的旧密码明文
	 * @param newPass	提交的新密码明文
	 */
	public void modifyPassword(final String oldPass, final String newPass) {
		// 查询当前用户数据库中密码
		Account account = dao.get(Account.class, AuthenticationUtil.getInfo(AuthenticationUtil.LoginInfo.ACCOUNT_ID).toString());
		String savedPass = account.getPassword();
	
		//提交的新旧密码加密
		String submitOldPass = Encryptor.encryptPassword(oldPass);
		String submitNewPass = Encryptor.encryptPassword(newPass);

		if (StringUtils.isBlank(savedPass) && !StringUtils.isBlank(submitOldPass)) {
			throw new BusinessException("修改密码失败，原密码输入错误！");
		} else if (savedPass != null && !savedPass.equals(submitOldPass)) {
			throw new BusinessException("修改密码失败，原密码输入错误！");
		} else { // 修改密码
			account.setPassword(submitNewPass);
			this.update(account);
		}
	}

	/**
	 * 密码重置
	 * @param accountId 账户ID
	 */
	public void resetPassword(String accountId) {
		Account account = dao.get(Account.class, accountId);
		String defaultPass = SystemLoader.getConfigValue("DEFAULT_PASS", "123123");
		account.setPassword(Encryptor.encryptPassword(defaultPass));//初始化登录密码
		this.update(account);
	}
	
	/**
	 * 检验人员是否注册过登录账号
	 * @param personId 人员名称
	 * @return true 注册过；false 未注册
	 */
	public Boolean hasRegisted(final String personId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Account.class);
		criteria.add(Restrictions.eq("personId", personId));
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		return dao.getCountByCriteria(criteria) > 0;
	}
	
	/**
	 * 检验登录名是否重复
	 * @param loginName 待验证名称
	 * @return true 重复；false 不重复
	 */
	public Boolean loginNameExist(final String loginName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Account.class);
		criteria.add(Restrictions.eq("name", loginName));
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		return dao.getCountByCriteria(criteria) > 0;
	}

	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(Account entity) {
		entity.setPassword(Encryptor.encryptPassword(defaultPassword));//初始化登录密码
		entity.setEnabled(true);
		entity.setSuperAccount(false);
	}

	@Override
	protected void beforeDelete(Account entity) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AccountRole.class);
		criteria.add(Restrictions.eq("accountId", entity.getId()));
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		if (dao.getCountByCriteria(criteria) > 0) {
			throw new BusinessException("删除失败,该用户在使用中！");
		}
	}

	@Override
	protected void beforeUpdate(Account entity) {}
}