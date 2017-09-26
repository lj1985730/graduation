package com.graduation.core.base.service;

import com.graduation.core.base.entity.TreeEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * 业务层-树状实体抽象类<br>
 * 使用泛型约束，如无必须实体类对应，可以用TreeEntity充当<br>
 * 用于业务Service层继承，比BaseService额外提供了树状数据的操作
 * @author Liu Jun
 * @version 2016-8-12 00:06:11
 */
public abstract class TreeService<E extends TreeEntity> extends BaseService<E> {

    /**
     * 根据id检验欲删除的数据是否是叶节点
     * @param id 节点Id
     * @return true 是叶子节点；false 不是
     */
    protected Boolean isLeaf(final String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
        // 构造条件
        criteria.add(Restrictions.eq("parentId", id));
        criteria.add(Restrictions.eq(DELETE_PARAM, false));
        return dao.getCountByCriteria(criteria) == 0;
    }

    /**
     * 节点 使用/禁用
     * @param id 节点Id
     * @param enable true 启用；false 禁用
     */
    public void triggerEnable(final String id, final boolean enable) {
        String[] supObjIds = getAllSubObjId(id);

        if(supObjIds == null) {
            return;
        }

        for (String supMenuId : supObjIds) {
            E entity = dao.get(entityClass, supMenuId);
            entity.setEnabled(enable);
            this.update(entity);
        }
    }

    /**
     * 取对象所有子节点Id
     * @param id 目标对象Id
     * @return 自身以及子节Id数组
     */
    public String[] getAllSubObjId(String id) {
        if(StringUtils.isBlank(id)) {
            return null;
        }
        String[] result = new String[] { id };	//结果集合
        return getAllSubObjId(result, result);
    }

    /**
     * 递归获取全部子节点Id
     * @param result 结果集
     * @param parentIds 父Id数组
     * @return 全部子节点Id
     */
    private String[] getAllSubObjId(String[] result, String[] parentIds) {
        List<E> subObjList = getChildren(parentIds);
        if(subObjList == null) {
            return result;
        } else {
            String[] subObjIds = new String[subObjList.size()];
            for (int i = 0; i < subObjList.size(); i++) {
                subObjIds[i] = subObjList.get(i).getId();
            }
            result = ArrayUtils.addAll(result, subObjIds);
            return getAllSubObjId(result, subObjIds);	//递归调用
        }
    }

    /**
     * 获取所有子节点
     * @param parentIds	 父Id数组
     * @return 子节点集合
     */
    private List<E> getChildren(String[] parentIds) {
        if(parentIds == null || ArrayUtils.isEmpty(parentIds)) {
            return null;
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
        criteria.add(Restrictions.in("parentId", parentIds));
        criteria.add(Restrictions.eq(DELETE_PARAM, false));
        List<E> subList = dao.criteriaQuery(criteria);
        return (subList == null || subList.isEmpty()) ? null : subList;
    }
}