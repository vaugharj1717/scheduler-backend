package com.Services;

import com.Entities.Department;
import com.Entities.Position;

import java.util.List;

public interface DepartmentService {

    public List<Department> getAllDepartments();
    public void deleteDepartment(Integer id);
}
