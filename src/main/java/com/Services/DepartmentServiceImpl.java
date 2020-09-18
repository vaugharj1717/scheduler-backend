package com.Services;

import com.DAOs.DepartmentDAO;
import com.Entities.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentDAO departmentDAO;

    public List<Department> getAllDepartments(){
        List<Department> departmentList = departmentDAO.getAllDepartments();
        return departmentList;
    }
}
