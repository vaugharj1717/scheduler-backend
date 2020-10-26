package com.Controllers;

import com.Entities.Department;
import com.Entities.Position;
import com.Entities.User;
import com.Services.DepartmentService;
import com.Services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/department")
@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @RequestMapping(method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('SCHEDULER')")
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

    @RequestMapping(path = "/{departmentId}", method= RequestMethod.DELETE)
    @Transactional
    @PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<Integer> deleteDepartment(@PathVariable Integer departmentId){
        try{
            departmentService.deleteDepartment(departmentId);

            //success case
            return new ResponseEntity<Integer>(departmentId, HttpStatus.OK);
        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/create", method= RequestMethod.POST)
    @PreAuthorize("hasAuthority('SCHEDULER')")
    public ResponseEntity<Department> createDepartment(@RequestBody JsonNode body){
        try{
            String departmentName = body.get("departmentName").asText();
            Department department = departmentService.createDepartment(departmentName);

            //success case
            return new ResponseEntity<Department>(department, HttpStatus.OK);
        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Department>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
