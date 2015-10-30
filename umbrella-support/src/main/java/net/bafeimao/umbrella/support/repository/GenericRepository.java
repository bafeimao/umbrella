/*
 * Copyright 2002-2015 by bafeimao.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
