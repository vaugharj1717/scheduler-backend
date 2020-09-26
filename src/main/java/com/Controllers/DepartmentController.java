package com.Controllers;

import com.Entities.Department;
import com.Entities.Position;
import com.Entities.User;
import com.Services.DepartmentService;
import com.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/department")
@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<Department>> getAllDepartments(){
        try{
            List<Department> departmentList = departmentService.getAllDepartments();

            //success case
            return new ResponseEntity<List<Department>>(departmentList, HttpStatus.OK);
        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<Department>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
