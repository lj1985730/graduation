package com.graduation.authentication.service;

import com.graduation.authentication.entity.EducationExperience;
import com.graduation.core.base.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 权限-教育经历-业务层
 * @author Liu Jun
 * @version 2016-8-16 22:45:13
 */
@Service
@Transactional
public class EducationExperienceService extends BaseService<EducationExperience> {
	
	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(EducationExperience entity) {}

	@Override
	protected void beforeDelete(EducationExperience entity) {}

	@Override
	protected void beforeUpdate(EducationExperience entity) {}
}