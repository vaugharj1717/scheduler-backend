package com.DAOs;

import com.Entities.Department;
import com.Entities.Position;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


public interface DepartmentDAO extends DAO<Department> {
    public List<Position> getPositionsByDepartment(Integer id);
    public void removeDepartment(Integer id);
}
