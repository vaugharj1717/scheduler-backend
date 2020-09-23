package com.DAOs;

import com.Entities.Position;

import java.util.List;

public interface PositionDAO {
    public List<Position> getPositionsByDepartment (Integer id);
    public Position saveOrUpdate(Position position);
    public Position getById(Integer id);
    public List<Position> getAll();
}
