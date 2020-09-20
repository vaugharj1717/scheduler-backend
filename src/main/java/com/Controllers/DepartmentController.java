package com.Controllers;

import com.Entities.Department;
import com.Services.DepartmentService;
import com.Utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
//Controller's responsibilities
//  1) Use @RequestMapping annotations to match with the URL that the frontend is trying to access
//  2) "Unpack" any data that was sent from frontend (either in Request Body or in URL path)
//  3) Kick off service layer function to do rest of the work
//  3) Send JSON back to frontend with
//     "new ResponseEntity<>(objectToReturn, HttpStatus.OK/INTERNAL_SERVER_ERROR/BAD_REQUEST);"
//     OR
//     "new ResponseEntity<>(HttpStatus.OK/INTERNAL_SERVER_ERROR/BAD_REQUEST);"
//     if there is no JSON that needs to be returned
//          -Set HttpStatus.OK if successful
//          -Set HttpStatus.INTERNAL_SERVER_ERROR for error case
//          -Set HttpStatus.BAD_REQUEST if unsuccessful (for business logic reasons)
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Department>> getAllDepartments(){
        try {
            //Request all departments from service layer
            List<Department> departmentList = departmentService.getAllDepartments();
            //success case: return all departments
            return new ResponseEntity<List<Department>>(departmentList, HttpStatus.OK);
        }
        catch(Exception e){
            //error case, return error code
            return new ResponseEntity<List<Department>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
