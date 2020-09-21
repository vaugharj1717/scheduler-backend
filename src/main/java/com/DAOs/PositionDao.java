package com.DAOs;

import com.Entities.Position;

import java.util.List;

public interface PositionDAO extends DAO<Position>{

    public List<Position> getPositionsByDepartement (Integer id);

}
