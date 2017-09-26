package com.graduation.authentication.service;

import com.graduation.core.base.service.BaseService;
import com.graduation.authentication.entity.Button;
import com.graduation.authentication.entity.RoleButton;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 权限-角色按钮关系-业务层
 * @author Liu Jun
 * @version 2016-8-14 22:28:14
 */
@Service
@Transactional
public class RoleButtonService extends BaseService<RoleButton> {

	/**
	 * 获取角色的菜单
	 * @param roleId 账户Id
	 * @return 角色的菜单
	 */
	public List<Button> getRoleButton(String roleId) {
		DetachedCriteria roleButtonCriteria = DetachedCriteria.forClass(RoleButton.class);
		roleButtonCriteria.add(Restrictions.eq("roleId", roleId));
		roleButtonCriteria.add(Restrictions.eq(DELETE_PARAM, false));
		roleButtonCriteria.setProjection(Projections.property("buttonId"));

		DetachedCriteria buttonCriteria = DetachedCriteria.forClass(Button.class);
		buttonCriteria.add(Property.forName("id").eq(roleButtonCriteria));
		return dao.criteriaQuery(buttonCriteria);
	}

	/**
	 * 设置角色的菜单
	 * @param roleId 账户Id
	 * @param buttonIds 角色Id集合
	 */
	public void setRoleButton(String roleId, String[] buttonIds) {
		List<String> removedIdList = removeAllButton(roleId);	//先删除全部已绑定的数据

		List<String> newIdList = Arrays.asList(buttonIds);	//需要绑定的数据

		List<String> updateIdList = new ArrayList<>();	//绑定时需要更新的数据
		List<String> createIdList = new ArrayList<>();	//绑定时需要新增的数据

		newIdList.forEach(newId -> {	//saveOrUpdate分发
			if(removedIdList.contains(newId)) {
				updateIdList.add(newId);
			} else {
				createIdList.add(newId);
			}
		});

		updateRoleButton(roleId, updateIdList);

		createRoleButton(roleId, createIdList);
	}

	/**
	 * 删除角色的全部按钮
	 * @param roleId 角色Id
	 * @return 被删除的按钮Id
	 */
	private List<String> removeAllButton(String roleId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RoleButton.class);
		// 构造条件
		criteria.add(Restrictions.eq("roleId", roleId));
		criteria.add(Restrictions.eq(DELETE_PARAM, false));
		List<RoleButton> list = dao.criteriaQuery(criteria);

		List<String> removedButtonId = new ArrayList<>();

		list.forEach(roleMenu -> {
			this.delete(roleMenu);
			removedButtonId.add(roleMenu.getButtonId());
		});

		return removedButtonId;
	}

	/**
	 * 生效角色的全部按钮
	 * @param roleId 角色Id
	 * @param buttonIdList 待操作按钮Id集合
	 */
	private void updateRoleButton(String roleId, List<String> buttonIdList) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RoleButton.class);
		// 构造条件
		criteria.add(Restrictions.eq("roleId", roleId));
		criteria.add(Restrictions.in("buttonId", buttonIdList));
		criteria.add(Restrictions.eq(DELETE_PARAM, true));
		List<RoleButton> list = dao.criteriaQuery(criteria);

		list.forEach(this :: update);
	}

	/**
	 * 新增角色的全部按钮
	 * @param roleId		角色Id
	 * @param buttonIdList 待操作按钮Id集合
	 */
	private void createRoleButton(String roleId, List<String> buttonIdList) {
		RoleButton roleButton;
		for(String buttonId : buttonIdList) {
			roleButton = new RoleButton();
			roleButton.setRoleId(roleId);
			roleButton.setButtonId(buttonId);
			this.create(roleButton);
		}
	}

	@Override
	protected List<Map<String, String>> genTotalRow() {
		return null;
	}

	@Override
	protected void beforeCreate(RoleButton entity) {}

	@Override
	protected void beforeDelete(RoleButton entity) {}

	@Override
	protected void beforeUpdate(RoleButton entity) {}
}