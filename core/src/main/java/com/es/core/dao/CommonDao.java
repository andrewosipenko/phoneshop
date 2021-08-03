package com.es.core.dao;

import java.util.List;

public interface CommonDao<T> {

    List<T> findAll(int offset, int limit);

    void save(T model);

    void update(T model);

    void delete(T model);


}
