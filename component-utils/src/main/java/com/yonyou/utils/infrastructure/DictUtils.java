package com.yonyou.utils.infrastructure;

import com.graduation.core.base.mapper.JsonMapper;
import com.graduation.core.base.util.SpringContextUtils;
import com.yonyou.utils.application.service.DictService;
import com.yonyou.utils.model.SystemDict;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static DictService dictService = SpringContextUtils.getBean(DictService.class);

	public static final String CACHE_DICT_MAP = "DICT_MAP";

	/**
	 *获取字典名称
	 * @param code 字典编号
	 * @param category 分类
	 * @param defaultValue
	 * @return
	 */
	public static String getDictName(String code, String category, String defaultValue){
		if (StringUtils.isNotBlank(category) && StringUtils.isNotBlank(code)){
			for (SystemDict dict : getDictList(category)) {
				if (category.equals(dict.getCategory()) && code.equals(dict.getCode())){
					return dict.getName();
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 获取字典名称组
	 * @param codes 字典编号组，','分割
	 * @param category 字典分类
	 * @param defaultNames 为null时返回默认值
	 * @return
	 */
	public static String getDictNames(String codes, String category, String defaultNames) {
		if (StringUtils.isNotBlank(category) && StringUtils.isNotBlank(codes)) {
			List<String> valueList = new ArrayList<>();
			for (String value : StringUtils.split(codes, ",")){
				valueList.add(getDictName(value, category, defaultNames));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultNames;
	}

	/**
	 * 获取字典编号
	 * @param name 字典名称
	 * @param category 分类
	 * @param defaultCode 为null时返回默认值
	 * @return 字典编号
	 */
	public static String getDictCode(String name, String category, String defaultCode) {
		if (StringUtils.isNotBlank(category) && StringUtils.isNotBlank(name)){
			for (SystemDict dict : getDictList(category)){
				if (category.equals(dict.getCategory()) && name.equals(dict.getName())){
					return dict.getCode();
				}
			}
		}
		return defaultCode;
	}

	/**
	 * 根据分类获取字典内容
	 * @param category 分类
	 * @return 字典内容
	 */
	public static List<SystemDict> getDictList(String category) {
		@SuppressWarnings("unchecked")
		Map<String, List<SystemDict>> dictMap = (Map<String, List<SystemDict>>)EhCacheUtils.get(CACHE_DICT_MAP);
		if (dictMap == null){
			dictMap = new HashMap<>();
			for (SystemDict dict : dictService.searchAll()) {
				List<SystemDict> dictList = dictMap.get(dict.getCategory());
				if (dictList != null){
					dictList.add(dict);
				} else {
					dictList = new ArrayList<>();
					dictList.add(dict);
					dictMap.put(dict.getCategory(), dictList);
				}
			}
			EhCacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<SystemDict> dictList = dictMap.get(category);
		if (dictList == null){
			dictList = new ArrayList<>();
		}
		return dictList;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param category 字典分类
	 * @return 典列表（JSON）
	 */
	public static String getDictListJson(String category){
		return JsonMapper.toJsonString(getDictList(category));
	}
	
}
