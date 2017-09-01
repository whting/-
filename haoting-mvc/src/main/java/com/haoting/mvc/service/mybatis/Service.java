package com.haoting.mvc.service.mybatis;

import com.haoting.mvc.model.Pagination;
import com.haoting.mvc.model.PersistentObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Service接口
 * 
 * @param <T>
 * @param <ID>
 * @author Joe
 */
public interface Service<T extends PersistentObject, ID extends Serializable> {

	/**
	 * 查询所有分页
	 * 
	 * @param p
	 * @return
	 */
	public Pagination<T> findByAllPagination(Pagination<T> p);

	/**
	 * 通过主键查询实体
	 * 
	 * @param PK
	 *            pk
	 * @return T
	 */
	public T get(ID pk);

	/**
	 * 通过主键集合查询实体
	 * 
	 * @param List
	 *            <PK> pks
	 * @return List<T>
	 */
	public List<T> get(Collection<ID> pks);

	/**
	 * 插入/更新实体
	 * 
	 * @param T
	 *            t
	 */
	public void save(T t);

	/**
	 * 插入/更新实体集合
	 * 
	 * @param List
	 *            <T> ts
	 */
	public void save(Collection<T> ts);

	/**
	 * 更新实体
	 * 
	 * @param T
	 *            t
	 */
	public void update(T t);

	/**
	 * 更新实体集合
	 * 
	 * @param List
	 *            <T> ts
	 */
	public void update(Collection<T> ts);

	/**
	 * 删除实体
	 * 
	 * @param T
	 *            t
	 */
	public void delete(T t);

	/**
	 * 删除实体集合
	 * 
	 * @param List
	 *            <T> ts
	 */
	public void delete(Collection<T> ts);

	/**
	 * 通过主键删除实体
	 * 
	 * @param PK
	 *            pk
	 * @return T
	 */
	public void deleteById(ID id);

	/**
	 * 通过主键集合删除实体 注：这里别把List改为Collection，会导致覆盖方法的List<ID>调用不到
	 * 
	 * @param List
	 *            <PK> pks
	 * @return List<T>
	 */
	public void deleteById(List<ID> idList);
}
