package com.xcrm.system.user.dao.base;

import java.util.List;
import java.util.Map;

public interface IBaseDao<T> {
	/**
	 * 根据主键查
	 * @param map 
	 * @return
	 */
	T findById(Object o) throws Exception;
	
	/**
	 * 查所有
	 * @return
	 */
	List<T> list() throws Exception;
	
	/**
	 * 分页查
	 * @return
	 */
	List<T> list(Map<String, Object> map) throws Exception;
	
	/**
	 * 增
	 * @param t
	 * @return
	 */
	int add(T t) throws Exception;
	
	/**
	 * 删
	 * @param o
	 * @return
	 */
	int delete(Object o) throws Exception;
	
	/**
	 * 改
	 * @param t
	 * @return
	 */
	int update(T t) throws Exception;

}
