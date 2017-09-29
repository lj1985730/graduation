/*
 * BaseDao.java Copyright 2015 LandSea, Inc. All rights reserved.
 */
package com.graduation.core.base.dao;

import com.graduation.core.base.entity.BaseEntity;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 *  核心-基础-Hibernate DAO层
 * @author liujun
 */
@Repository
public class BaseDao {

	/**
	 * 注入SessionFactory
	 */
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * 封装保存
	 * @param entity 待保存对象
	 */
	public <E extends BaseEntity> Serializable save(final E entity) {
		Session session = this.getSession();
		return session.save(entity);
	}
	
	/**
	 * 封装更新
	 * @param entity 待更新对象
	 */
	public <E extends BaseEntity> void update(final E entity) {
		Session session = this.getSession();
		session.update(entity);
	}

	/**
	 * 封装保存或更新
	 * @param entity 待保存或更新对象
	 */
	public <E extends BaseEntity> void saveOrUpdate(final E entity) {
		Session session = this.getSession();
		session.saveOrUpdate(entity);
	}

	/**
	 * 封装个别字段更新更新
	 * @param entity 待更新对象
	 * @param id 更新对象主键
	 */
	public <E extends BaseEntity> void updateDefault(final E entity, String id) {
		E oldEntity;
		try {
			oldEntity = this.get(entity.getClass().getName(), id);
			copyPropertiesIgnoreNull(entity, oldEntity);
			Session session = this.getSession();
			session.update(oldEntity);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 封装按ID删除
	 * @param clazz 待删除对象类型
	 * @param id 待删除对象ID
	 */
	public <E extends BaseEntity> void delete(final Class<E> clazz, final Serializable id) {
		Session session = this.getSession();
		session.delete(session.get(clazz, id));
	}

	/**
	 * 封装删除
	 * @param entity 待删除对象
	 */
	public <E extends BaseEntity> void delete(final E entity) {
		Session session = this.getSession();
		session.delete(entity);
	}

	/**
	 * 封装按ID数组批量删除
	 * @param clazz 待删除对象类型
	 * @param ids 待删除对象ID集合
	 */
	public <E extends BaseEntity> void deleteAll(final Class<E> clazz, String[] ids) {
		Session session = this.getSession();
		for(String id : ids) {
			session.delete(session.get(clazz, id));
		}
	}

	/**
	 * 封装按ID和类名查询
	 * @param className 查询对象类型
	 * @param id 查询对象ID
	 * @return 查询对象
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public <E extends BaseEntity> E get(final String className, final Serializable id) throws ClassNotFoundException {
		Session session = this.getSession();
		return (E) session.get(Class.forName(className), id);
	}

	/**
	 * 封装按ID和类型查询
	 * @param clazz 查询对象类型
	 * @param id 查询对象ID
	 * @return 查询对象
	 */
	public <E extends BaseEntity> E get(final Class<E> clazz, final Serializable id) {
		Session session = this.getSession();
		//采用get方法，查询不到返回NULL，load会抛出异常
		return session.get(clazz, id);
	}

	/**
	 * 封装按类型查询全部对象
	 * @param clazz 待查询对象类型
	 * @return List 待查询对象集合
	 */
	@SuppressWarnings("unchecked")
	public <E extends BaseEntity> List<E> getAll(final Class<E> clazz) {
		Session session = this.getSession();
		String hql = "From " + clazz.getName();
		Query query = session.createQuery(hql);
		return (List<E>) query.list();
	}
	
	/**
	 * 封装按类型排序查询全部对象
	 * @param clazz 待查询对象类型
	 * @param orderColumn 排序属性名
	 * @param order ASC or DESC
	 * @return List 待查询对象集合
	 */
	@SuppressWarnings("unchecked")
	public <E extends BaseEntity> List<E> getAll(final Class<E> clazz, String orderColumn, String order) {
		Session session = this.getSession();
		String hql = "From " + clazz.getName() + " Order By " + orderColumn + " " + order;
		Query query = session.createQuery(hql);
		return (List<E>) query.list();
	}

	/**
	 * 封装根据DetachedCriteria查询
	 * @param detachedCriteria QBC查询对象
	 * @return List 查询不分页对象集合
	 */
	public <E> List<E> criteriaQuery(DetachedCriteria detachedCriteria) {
		Session session = this.getSession();
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		List<E> list = (List<E>) criteria.list();
		return list;
	}

	/**
	 * 封装根据DetachedCriteria查询（分页）
	 * @param detachedCriteria	QBC查询对象
	 * @param start 起始数据序号
	 * @param limit 每页数据条数
	 * @return 分页查询对象集合
	 */
	@SuppressWarnings("unchecked")
	public <E extends BaseEntity> List<E> criteriaPageQuery(DetachedCriteria detachedCriteria, Integer start, Integer limit) {
		Session session = this.getSession();
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		criteria.setFirstResult(start).setMaxResults(limit);
		return (List<E>) criteria.list();
	}

	/**
	 * 封装根据DetachedCriteria查询
	 * @param detachedCriteria	查询对象
	 * @return Long 查询对象集合总数
	 */
	public Long getCountByCriteria(DetachedCriteria detachedCriteria) {
		Session session = this.getSession();
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
//		CriteriaImpl impl = (CriteriaImpl) criteria;
//		Projection projection = impl.getProjection();
		Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
//		criteria.setProjection(projection);
//		if (projection == null) {
//			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
//		}
		return count;
	}
	
	/**
	 * 封装根据hql查询
	 * @param hql 查询hql
	 * @return List 查询对象集合
	 */
	public <E extends BaseEntity> List<E> hqlQuery(final String hql) {
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		return (List<E>) query.list();
	}

	/**
	 * 封装根据hql查询数据
	 * @param hql 查询hql
	 * @param params hql中对应参数数组
	 * @return 查询结果集
	 */
	public <E> List<E> hqlQuery(final String hql, final Object[] params) {
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		setQueryParameter(query, params);
		return (List<E>) query.list();
	}

	/**
	 * 封装根据hql修改数据(insert、update、delete)
	 * @param hql 修改操作hql
	 * @return The number of entities updated or deleted.
	 */
	public int hqlUpdate(final String hql) {
		Session session = this.getSession();
		return session.createQuery(hql).executeUpdate();
	}

	/**
	 * 封装根据hql修改数据(insert、update、delete)
	 * @param hql 修改操作hql
	 * @param params hql中对应参数数组
	 * @return The number of entities updated or deleted.
	 */
	public int hqlUpdate(final String hql, final Object[] params) {
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		setQueryParameter(query, params);
		return query.executeUpdate();
	}

	private void setQueryParameter(final Query query, final Object[] params) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				Object obj = params[i];
				if (obj == null) {
					continue;
				}
				if (obj instanceof String) {
					query.setString(i, (String) obj);
				} else if (obj instanceof Integer) {
					query.setInteger(i, (Integer) obj);
				} else if (obj instanceof Long) {
					query.setLong(i, (Long) obj);
				} else if (obj instanceof Float) {
					query.setFloat(i, (Float) obj);
				} else if (obj instanceof Double) {
					query.setDouble(i, (Double) obj);
				} else if (obj instanceof Date) {
					query.setTimestamp(i, (Date) obj);
				} else {
					query.setParameter(i, obj);
				}
			}
		}
	}

	/**
	 * 封装根据sql查询
	 * @param sql 查询sql
	 * @return List 查询对象集合
	 */
	public List<Map<String, String>> sqlQuery(final String sql) {
		Session session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	/**
	 * 封装根据sql按照多个条件查询
	 * @param sql 修改操作hql
	 * @param params sql中对应参数数组
	 * @return The number of entities updated or deleted.
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> sqlQuery(final String sql, final Object[] params) {
		Session session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		setSQLQueryParameter(query, params);
		return query.list();
	}

	/**
	 * 封装根据sql修改数据(insert、update、delete)
	 * @param sql 修改操作sql
	 * @return The number of entities updated or deleted.
	 */
	public int sqlUpdate(final String sql) {
		Session session = this.getSession();
		return session.createSQLQuery(sql).executeUpdate();
	}

	/**
	 * 封装根据sql修改数据(insert、update、delete)
	 * @param sql 修改操作sql
	 * @param params hql中对应参数数组
	 * @return The number of entities updated or deleted.
	 */
	public int sqlUpdate(final String sql, final Object[] params) {
		Session session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		setSQLQueryParameter(query, params);
		return query.executeUpdate();
	}

	private void setSQLQueryParameter(final SQLQuery query, final Object[] params) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				Object obj = params[i];
				if (obj == null) {
					continue;
				}
				if (obj instanceof String) {
					query.setString(i, (String) obj);
				} else if (obj instanceof Integer) {
					query.setInteger(i, (Integer) obj);
				} else if (obj instanceof Long) {
					query.setLong(i, (Long) obj);
				} else if (obj instanceof Float) {
					query.setFloat(i, (Float) obj);
				} else if (obj instanceof Double) {
					query.setDouble(i, (Double) obj);
				} else {
					query.setParameter(i, obj);
				}
			}
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 获取数据库连接，session绑定，不建议使用
	 * @return 数据库连接对象
	 */
	public Connection getConnection() throws SQLException {
		return SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
	}

	/**
	 * 获取空值的属性名称数组
	 * @param source	实体类对象
	 * @return 空值的属性名称数组
	 */
	private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || "".equals(srcValue) || "0".equals(srcValue.toString())) {
            	emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

	/**
	 * 拷贝对象属性，忽略空值属性
	 * @param src	源对象
	 * @param target	目标对象
	 */
    public void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}