package com.Services;

import com.Entities.Position;

import java.util.List;

public interface PositionService {
    public List<Position> getPositionsByDepartment (Integer id);
}