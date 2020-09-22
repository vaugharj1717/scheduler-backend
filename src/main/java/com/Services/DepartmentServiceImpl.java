package com.Services;

import com.DAOs.DepartmentDAO;
import com.Entities.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentDAO departmentDAO;

    @Transactional
    public List<Department> getAllDepartments() {

        return departmentDAO.getAll();
    }
}
