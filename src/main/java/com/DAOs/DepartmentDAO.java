package com.DAOs;

import com.Entities.Department;

import java.util.List;

public interface DepartmentDAO {
    public List<Department> getAllDepartments();

    public void saveDepartment(Department department);
}
