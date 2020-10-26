package com.Services;

import com.DAOs.DepartmentDAO;
import com.DAOs.PositionDAO;
import com.Entities.Department;
import com.Entities.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentDAO departmentDAO;
    @Autowired
    PositionDAO positionDao;

    @Transactional
    public List<Department> getAllDepartments() {

        return departmentDAO.getAll();
    }

    @Override
    @Transactional
    public void deleteDepartment(Integer id) {
        departmentDAO.removeDepartment(id);
    }

    @Override
    public Department createDepartment(String departmentName) {
        Department department = new Department();
        department.setDepartmentName(departmentName);
        return departmentDAO.saveOrUpdate(department);
    }
}
