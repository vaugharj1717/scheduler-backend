package com.Services;

import com.DAOs.DepartmentDAO;
import com.Entities.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


//Service layer responsibilities
//  1) Take care of any business logic
//  2) Use DAO classes to access any data necessary
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentDAO departmentDAO;

    public List<Department> getAllDepartments(){
        //no business logic necessary for this endpoint, just request all departments from DAO layer
        List<Department> departmentList = departmentDAO.getAllDepartments();
        return departmentList;
    }
}
