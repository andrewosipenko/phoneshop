package com.es.core.dao;

import java.util.List;

public interface CommonDao<T> {

    List<T> findAll(int offset, int limit);

    void save(final T model);

}
