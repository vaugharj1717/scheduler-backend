package com.DAOs;

import com.Entities.Department;
import com.Entities.Position;

import java.util.List;


public interface DepartmentDAO extends DAO<Department> {
    public List<Position> getPositionsByDepartment(Integer id);
}
