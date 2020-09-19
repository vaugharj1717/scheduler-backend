package com.DAOs;

import com.Entities.Position;

import java.util.List;

public interface PositionDao {
    public List<Position> getPositionsByDepartement (Integer id);
}
