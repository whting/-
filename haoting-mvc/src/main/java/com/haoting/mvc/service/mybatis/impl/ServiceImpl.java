package com.haoting.mvc.service.mybatis.impl;

import com.haoting.mvc.dao.mybatis.Mapper;
import com.haoting.mvc.exception.DaoException;
import com.haoting.mvc.model.Pagination;
import com.haoting.mvc.model.PersistentObject;
import com.haoting.mvc.service.mybatis.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Service基类，实现了数据的CRUD
 * 
 * @param <MAPPER>
 * @param <T>
 * @param <ID>
 * @author Joe
 */
public abstract class ServiceImpl<MAPPER extends Mapper<T, ID>, T extends PersistentObject, ID extends Serializable>
		implements Service<T, ID> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceImpl.class);

	/**
	 * 由子类注入实体DAO
	 */
	protected MAPPER mapper;

	public abstract void setDao(MAPPER mapper);

	/**
	 * 查询所有分页
	 * 
	 * @param p
	 * @return
	 */
	public Pagination<T> findByAllPagination(Pagination<T> p) {
		mapper.findByAll(p);
		return p;
	}

	/**
	 * 通过主键查询实体
	 * 
	 * @param PK
	 *            pk
	 * @return T
	 */
	public T get(ID pk) {
		return mapper.get(pk);
	}

	/**
	 * 通过主键集合查询实体
	 * 
	 * @param List
	 *            <PK> pks
	 * @return List<T>
	 */
	public List<T> get(Collection<ID> pks) {
		List<T> list = new ArrayList<T>(pks.size());
		for (ID pk : pks) {
			list.add(get(pk));
		}
		return list;
	}

	/**
	 * 插入/更新实体
	 * 
	 * @param T
	 *            t
	 * 
	 */
	public void save(T t) {
		if (t.getId() == null) {
			mapper.insert(t);
		}
		else {
			mapper.update(t);
		}
	}

	/**
	 * 插入/更新实体集合
	 * 
	 * @param List
	 *            <T> ts
	 */
	public void save(Collection<T> ts) {
		for (T t : ts) {
			save(t);
		}
	}

	/**
	 * 更新实体
	 * 
	 * @param T
	 *            t
	 */
	public void update(T t) {
		verifyRows(mapper.update(t), 1, "数据库更新失败");
	}

	/**
	 * 更新实体集合
	 * 
	 * @param List
	 *            <T> ts
	 */
	public void update(Collection<T> ts) {
		for (T t : ts) {
			update(t);
		}
	}

	/**
	 * 删除实体
	 * 
	 * @param T
	 *            t
	 */
	@SuppressWarnings("unchecked")
	public void delete(T t) {
		deleteById((ID) t.getId());
	}

	/**
	 * 删除实体集合
	 * 
	 * @param List
	 *            <T> ts
	 */
	public void delete(Collection<T> ts) {
		for (T t : ts) {
			delete(t);
		}
	}

	/**
	 * 通过主键删除实体
	 * 
	 * @param PK
	 *            pk
	 * @return T
	 */
	public void deleteById(ID id) {
		deleteById(Arrays.asList(id));
	}

	/**
	 * 通过主键集合删除实体 注：这里别把List改为Collection，会导致覆盖方法的List<ID>调用不到
	 * 
	 * @param List
	 *            <PK> pks
	 * @return List<T>
	 */
	public void deleteById(List<ID> idList) {
		verifyRows(mapper.deleteById(idList), idList.size(), "数据库删除失败");
	}

	/**
	 * 为高并发环境出现的更新和删除操作，验证更新数据库记录条数
	 * 
	 * @param updateRows
	 * @param rows
	 * @param message
	 */
	protected void verifyRows(int updateRows, int rows, String message) {
		if (updateRows != rows) {
			DaoException e = new DaoException(message);
			LOGGER.error("need update is {}, but real update rows is {}.", rows, updateRows, e);
			throw e;
		}
	}
}
