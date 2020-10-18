package com.DAOs;

import com.Entities.Location;

public interface LocationDAO extends DAO<Location>{
    public void remove(Integer id);
}
