package com.graduation.bus.application.service;

import com.graduation.bus.infrastructure.entity.Station;
import com.graduation.core.base.service.BaseService;
import com.graduation.core.system.entity.SystemComboData;
import com.graduation.core.system.entity.SystemDict;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础-系统参数-业务层
 * @author Liu Jun
 * @since 2016-8-2 22:43:06
 */
@Service
@Transactional
public class SystemComboDataService extends BaseService<SystemComboData> {

	@Override
	public List<Map<String, String>> genTotalRow() {
		return null;
	}	//自定义合计行内容

	/**
	 * 加载系统参数
	 */
	public List<Map<String, String>> loadComboSql(String key) {
		switch (key) {
			case "STATION":
				return this.loadStationCombo();
			case "BUS_CATEGORY":
				return this.loadBusCategoryCombo();
			default:
				return null;
		}
	}

	/**
	 * 加载系统参数
	 */
	private List<Map<String, String>> loadStationCombo() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Station.class);
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		criteria.addOrder(Order.asc("name"));
		List<Station> list = this.searchByCriteria(criteria);
		Map<String, String> map;
		List<Map<String, String>> result = new ArrayList<>();
		for (Station station : list) {
			map = new HashMap<>();
			map.put("id", station.getId());
			map.put("value", station.getName());
			result.add(map);
		}
		return result;
	}

	/**
	 * 加载系统参数
	 */
	private List<Map<String, String>> loadBusCategoryCombo() {
		DetachedCriteria criteria = DetachedCriteria.forClass(SystemDict.class);
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		criteria.add(Restrictions.eq("category", "BUS_CATEGORY"));
		criteria.addOrder(Order.asc("sort"));
		List<SystemDict> list = this.searchByCriteria(criteria);
		Map<String, String> map;
		List<Map<String, String>> result = new ArrayList<>();
		for (SystemDict dict : list) {
			map = new HashMap<>();
			map.put("id", dict.getId());
			map.put("value", dict.getName());
			result.add(map);
		}
		return result;
	}

	@Override
	public void beforeCreate(SystemComboData entity) {
	}

	@Override
	public void beforeDelete(SystemComboData entity) {
	}

	@Override
	public void beforeUpdate(SystemComboData entity) {
	}
}