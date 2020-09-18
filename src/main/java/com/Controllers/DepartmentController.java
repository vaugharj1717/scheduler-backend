package com.Controllers;

import com.Entities.Department;
import com.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Department> getAllDepartments(){
        List<Department> departmentList = departmentService.getAllDepartments();
        return departmentList;
    }
}
