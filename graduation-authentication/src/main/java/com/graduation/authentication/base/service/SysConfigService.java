package com.graduation.authentication.base.service;

import com.graduation.authentication.base.entity.SysConfig;
import com.graduation.core.base.exception.BusinessException;
import com.graduation.core.base.service.BaseService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 基础-系统参数-业务层
 * @author Liu Jun
 * @since 2016-8-2 22:43:06
 */
@Service
public class SysConfigService extends BaseService<SysConfig> {

	private static Logger logger = LoggerFactory.getLogger(SysConfigService.class);

	@Override
	public List<Map<String, String>> genTotalRow() {
		return null;
	}	//自定义合计行内容

	/**
	 * 加载系统参数
	 */
	public void loadSystemConfig() {
		logger.debug("加载系统参数...");
		DetachedCriteria criteria = DetachedCriteria.forClass(SysConfig.class);
		criteria.add(Restrictions.eq("enable", true));
		criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));
		List<SysConfig> configList = this.searchByCriteria(criteria);
		SystemLoader.configMap.clear();
		for (SysConfig conf : configList) {
			String key = conf.getKey();
			try {
				SystemLoader.configMap.put(key, conf.getValue());
				logger.debug("加载系统参数:" + key + "=" + conf.getValue());
			} catch (Exception ex) {
				logger.error("加载系统参数（失败）:" + key + "=" + conf.getValue());
			}
		}
		logger.debug("加载系统参数完毕.");
	}

	@Override
	public void beforeCreate(SysConfig entity) {
		entity.setEnable(true);
		entity.setEditable(true);
	}

	@Override
	public void beforeDelete(SysConfig entity) {
		if(!entity.getEditable()) {
			throw new BusinessException("该项目禁止编辑！");
		}
	}

	@Override
	public void beforeUpdate(SysConfig entity) {
		if(!entity.getEditable()) {
			throw new BusinessException("该项目禁止编辑！");
		}
	}
}