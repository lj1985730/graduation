package com.graduation.core.base.service;

import com.graduation.core.base.dao.BaseDao;
import com.graduation.core.base.dto.JsonResult;
import com.graduation.core.base.dto.Page;
import com.graduation.core.base.entity.BaseEntity;
import com.graduation.core.base.util.OrderByPinyin;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 业务层-基础抽象类<br>
 * 使用泛型约束，如无必须实体类对应，可以用BaseEntity充当<br>
 * 用于业务Service层继承，提供了简单对实体类的CURD操作
 * @author Liu Jun
 * @version 2015-12-26 14:28:54
 */
@Transactional(transactionManager = "transactionManager")
public abstract class BaseService<E extends BaseEntity> {

	/**
	 * 删除字段属性名
	 */
	public static final String DELETE_PARAM = "deleted";

	/**
	 * 排序号字段属性名
	 */
	public static final String SORT_PARAM = "sort";
	
	/**
	 * 统一注入DAO层
	 */
	@Resource(name="baseDao")
	protected BaseDao dao;
	
	/**
	 * 继承类泛型类型
	 */
	Class<E> entityClass;
	
	/**
	 * 排序父集合，处理多个查询的排序分配
	 */
	private Map<String, List<Order>> orderMap = new HashMap<>();

	private static final String QUERY_NAME = "lsQuery";
	
	/**
	 * 反射获取泛型Class
	 */
	public BaseService() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class<E>) params[0];
	}
	
	/**
	 * 通过分页的形式返回查询数据
	 * @param criteria	查询对象
	 * @param start	当前数据序号
	 * @param limit	每页显示数
	 * @param hasTotal	（可选，不传参默认为false）是否显示合计行
	 * @return	分页数据
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<E> searchPage(DetachedCriteria criteria, Integer start, Integer limit, Boolean... hasTotal) {
		return this.searchPage(criteria, start, limit, QUERY_NAME, hasTotal);
	}

	/**
	 * 通过分页的形式返回查询数据
	 * @param criteria	查询对象
	 * @param start	当前数据序号
	 * @param limit	每页显示数
	 * @param queryName	查询标识，用于区分单service中多个查询
	 * @param hasTotal	（可选，不传参默认为false）是否显示合计行
	 * @return	分页数据
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<E> searchPage(DetachedCriteria criteria, Integer start, Integer limit, String queryName, Boolean... hasTotal) {
		// 创建分页对象
		Page<E> pager = new Page<>(start, limit);
		long total = dao.getCountByCriteria(criteria);
		//排序
		if(orderMap != null && orderMap.containsKey(queryName)) {
			List<Order> orderList = orderMap.get(queryName);
			orderList.forEach(criteria::addOrder);
		}
		// 执行查询
		List<E> pageData = dao.criteriaPageQuery(criteria, start, limit);
		// 为pager绑定数据和总数
		pager.setRows(pageData);// 单页数据
		pager.setTotal(total);// 当前条件下的总数
		if(hasTotal != null && hasTotal.length != 0 && hasTotal[0]) {
			pager.setFooter(this.genTotalRow());// 合计列
		}
		return pager;
	}
	
	/**
	 * 增加排序
	 * @param sortProperty 排序字段
	 * @param order		asc or desc
	 * @param queryName 可选，查询标识，不填为默认,用于区分单service中多个查询
	 */
	public void addOrder(String sortProperty, String order, String... queryName) {
		addOrder(sortProperty, order, false, queryName);
	}

	/**
	 * 增加拼音排序
	 * @param sortProperty 排序字段
	 * @param order		asc or desc
	 * @param queryName 可选，查询标识，不填为默认,用于区分单service中多个查询
	 */
	public void addPYOrder(String sortProperty, String order, String... queryName) {
		addOrder(sortProperty, order, true, queryName);
	}
	
	/**
	 * 增加排序
	 * @param sortProperty 排序字段
	 * @param order		asc or desc
	 * @param isPinyin		是否拼音排序
	 * @param queryName 可选，查询名称，不填为默认
	 */
	private void addOrder(String sortProperty, String order, Boolean isPinyin, String... queryName) {
		String queryNameStr = QUERY_NAME;
		if(queryName != null && queryName.length > 0) {
			queryNameStr = queryName[0];
		}
		
		List<Order> orderList;
		if(orderMap != null && orderMap.containsKey(queryNameStr)) {
			orderList = orderMap.get(queryNameStr);
		} else {
			orderList = new ArrayList<>();
		}
		removeOrder(orderList, sortProperty);
		if ("asc".equalsIgnoreCase(order)) {
			if(isPinyin) {
				orderList.add(0, OrderByPinyin.asc(sortProperty));
			} else {
				orderList.add(0, Order.asc(sortProperty));
			}
		} else {
			if(isPinyin) {
				orderList.add(0, OrderByPinyin.desc(sortProperty));
			} else {
				orderList.add(0, Order.desc(sortProperty));
			}
		}
		orderMap.put(queryNameStr, orderList);
	}
	
	/**
	 * 如果参数在排序缓存中存在，则将其去除
	 * @param orderList	排序缓存
	 * @param property	排序字段
	 */
	private void removeOrder(List<Order> orderList, String property) {
		if (orderList.size() > 0) {
			for (Order order : orderList) {
				if (property.equals(order.getPropertyName())) {
					orderList.remove(order);
					break;
				}
			}
		}
	}
	
	/**
	 * 处理合计行，抽象方法，各实现类自己实现
	 * footerProp.put("housecode", "合计"); footerProp.put("usearea", "15");
	 * @return 合计行集合
	 */
	protected abstract List<Map<String, String>> genTotalRow();
	
	/**
	 * 获取全部数据，不分页
	 * @return 全部数据
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<E> searchAll() {
		return dao.getAll(entityClass);
	}
	
	/**
	 * 根据criteria获取数据，返回值类型不受基类泛型约束，取决于DetachedCriteria的对应实体
	 * @return 查询结果
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public <T extends BaseEntity> List<T> searchByCriteria(DetachedCriteria detachedCriteria) {
		return dao.criteriaQuery(detachedCriteria);
	}
	
	/**
	 * 根据sql获取数据
	 * @return 查询结果
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Map<String, String>> searchBySql(String sql) {
		return dao.sqlQuery(sql);
	}

	/**
	 * 根据hql获取数据，返回值类型不受基类泛型约束，取决于hql内容
	 * @return 查询结果
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public <T extends BaseEntity> List<T> searchByHql(String hql) {
		return dao.hqlQuery(hql);
	}

	/**
     * 查询单个实体对象
     * @param id 主键
     * @return 数据对象
     */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
    public E get(Serializable id) {
		return dao.get(entityClass, id);
    }

	/**
	 * 保存数据预处理<br>
	 * 可以对保存的数据进行部分默认字段的处理<br>
	 * 待系统封装AOP完善可弃用
	 * @param entity 需要处理的对象
	 */
	protected abstract void beforeCreate(E entity);

	/**
     * 新增单个实体对象
     * 基类中的isDel和uTime将被自动处理
     * @param entity	实体对象
     */
	@Transactional(propagation = Propagation.REQUIRED)
    public void create(E entity) {
		//前置处理
		beforeCreate(entity);
		entity.setDeleted(false);
		entity.setLastModifyTime(LocalDateTime.now());
		dao.save(entity);
    }

	/**
	 * 逻辑删除数据预处理<br>
	 * 可以对删除的数据进行部分删除前处理<br>
	 * 待系统封装AOP完善可弃用
	 * @param entity 需要处理的对象
	 */
	protected abstract void beforeDelete(E entity);

	/**
	 * 删除单个实体对象（逻辑删除）<br>
	 * 基类中的isDel和uTime将被自动处理
	 * @param id 待删除实体ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String id) {
		E entity = get(id);
		delete(entity);
	}

	/**
	 * 删除单个实体对象（逻辑删除）<br>
	 * 基类中的isDel和uTime将被自动处理
	 * @param entity 待删除实体
	 */
	public void delete(E entity) {
		beforeDelete(entity);
		entity.setDeleted(true);
		entity.setLastModifyTime(LocalDateTime.now());
		dao.update(entity);
	}

	/**
     * 删除单个实体对象（物理删除）
     * @param id 主键
     */
	@Transactional(propagation = Propagation.REQUIRED)
    public JsonResult destroy(Serializable id) {
		dao.delete(entityClass, id);
		return new JsonResult(true, JsonResult.DELETE_SUCCEED);
    }

	
	/**
	 * 更新数据预处理<br>
	 * 可以对删除的数据进行部分删除前处理<br>
	 * 待系统封装AOP完善可弃用
	 * @param entity 需要处理的对象
	 */
	protected abstract void beforeUpdate(E entity);
	
	/**
     * 更新单个实体对象
     * 基类中的isDel和uTime将被自动处理
     * @param entity 待更新实体对象
     */
	@Transactional(propagation = Propagation.REQUIRED)
    public void update(E entity) {
		beforeUpdate(entity);
		entity.setDeleted(false);
		entity.setLastModifyTime(LocalDateTime.now());
		dao.update(entity);
    }
}