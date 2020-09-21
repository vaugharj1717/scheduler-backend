package com.Controllers;

import com.Entities.User;
import com.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @RequestMapping("/participant")
    public List<User> getAllParticipants(){
        return userService.getAllParticipants();
    }
}