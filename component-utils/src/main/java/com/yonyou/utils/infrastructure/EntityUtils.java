package com.yonyou.utils.infrastructure;

import com.graduation.core.base.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 核心-实体类-帮助
 * Created by Liu on 2016/8/9.
 */
public class EntityUtils {

    /**
     * 获得所有entity的Id
     * @param entityList entity集合
     * @return Id集合
     */
    public static <E extends BaseEntity> List<String> getAllId(List<E> entityList) {
        if(entityList == null || entityList.isEmpty()) {
			return null;
		}
		List<String> entityIds = new ArrayList<>();
        entityList.forEach(entity -> entityIds.add(entity.getId()));
		return entityIds;
    }
}
