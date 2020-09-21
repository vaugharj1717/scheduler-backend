package com.DAOs;

import com.Entities.Department;
import com.Entities.Position;
import com.Entities.User;

import java.util.List;

public interface PositionDAO extends DAO<Position>{
    public List<Position> getPositionsByDepartement(Integer id);
}
