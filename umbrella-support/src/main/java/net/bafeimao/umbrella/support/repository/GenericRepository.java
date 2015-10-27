package net.bafeimao.umbrella.support.repository;

import java.io.Serializable;
import java.util.List;

public interface GenericRepository<T, K extends Serializable> {

	List<T> findAll();

	List<T> findAll(String queryString, Object... queryArgs);

	List<T> findAll(String queryString, Object[] queryArgs, Integer firstResult, Integer maxResult);

	T findById(String queryString, Object... queryArgs);

	T findById(K id);

	T load(K id);

	int executeNonQuery(String queryString, Object... queryArgs);

	Object executeScalar(String queryString, Object... queryArgs);

	Long count();

	Long count(String queryString, Object... queryArgs);

	boolean delete(K id);

	void delete(T entity);

	T save(T entity);
}
