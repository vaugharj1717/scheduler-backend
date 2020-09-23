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

    @Transactional
    @Override
    public Position createPositionToDepartment(String positionName, Integer idDepartement) {
        Position postion = new Position();
        postion.setPositionName(positionName);
        Department department = departmentDAO.getById(idDepartement);
        if(department == null) return null;
        else {
            postion.setDepartment(department);
            return positionDao.saveOrUpdate(postion);
        }
    }
}
