package com.yonyou.utils.application.service;

import com.graduation.core.base.service.BaseService;
import com.yonyou.utils.model.SystemDict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 工作流自定义仓储
 * @author Liu Jun at 2017-10-09 09:17:57
 * @since v1.0.0
 */
@Service
@Transactional
public class DictService extends BaseService<SystemDict> {

    @Override
    protected List<Map<String, String>> genTotalRow() {
        return null;
    }

    @Override
    protected void beforeCreate(SystemDict entity) {

    }

    @Override
    protected void beforeDelete(SystemDict entity) {

    }

    @Override
    protected void beforeUpdate(SystemDict entity) {

    }
}
