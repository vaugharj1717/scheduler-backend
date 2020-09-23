package com.DAOs;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();
    T getById (Integer id);
    void remove(Integer id);
    T saveOrUpdate(T domainObject);
}
