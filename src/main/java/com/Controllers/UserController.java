package com.Controllers;

import com.Entities.User;
import com.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/candidate")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllCandidates(){
        try{
            List<User> candidateList = userService.getAllCandidates();
            //success case
            return new ResponseEntity<List<User>>(candidateList, HttpStatus.OK);
        }
        //error case
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}