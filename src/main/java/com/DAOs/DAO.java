package com.DAOs;

public interface DAO<T>{
        T getById(Integer id);

        T saveOrUpdate(T domainObject);

        void delete(Integer id);
}
