package com.graduation.core.system.service;

import com.graduation.core.base.exception.BusinessException;
import com.graduation.core.base.service.BaseService;
import com.graduation.core.system.entity.SystemConfig;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 基础-系统参数-业务层
 * @author Liu Jun
 * @since 2016-8-2 22:43:06
 */
@Service
public class SystemConfigService extends BaseService<SystemConfig> {

	private static Logger logger = LoggerFactory.getLogger(SystemConfig.class);

	@Override
	public List<Map<String, String>> genTotalRow() {
		return null;
	}	//自定义合计行内容

	/**
	 * 加载系统参数
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	void loadSystemConfig() {
		DetachedCriteria criteria = DetachedCriteria.forClass(SystemConfig.class);
		criteria.add(Restrictions.eq("enable", true));
		criteria.add(Restrictions.eq(BaseService.DELETE_PARAM, false));
		List<SystemConfig> configList = this.searchByCriteria(criteria);
		SystemLoader.configMap.clear();
		for (SystemConfig conf : configList) {
			String key = conf.getKey();
			try {
				SystemLoader.configMap.put(key, conf.getValue());
			} catch (Exception ex) {
			}
		}
		logger.debug("加载系统参数完毕.");
	}

	@Override
	public void beforeCreate(SystemConfig entity) {
		entity.setEnable(true);
		entity.setEditable(true);
	}

	@Override
	public void beforeDelete(SystemConfig entity) {
		if(!entity.getEditable()) {
			throw new BusinessException("该项目禁止编辑！");
		}
	}

	@Override
	public void beforeUpdate(SystemConfig entity) {
		if(!entity.getEditable()) {
			throw new BusinessException("该项目禁止编辑！");
		}
	}
}