package com.Services;

import com.DAOs.UserDAO;
import com.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }


    @Override
    public User getUserById(Integer id) {
        //stub method for now
        return null;
    }

    @Override
    public void deleteUserById(Integer id) {
        //stub method for now
    }

    @Override
    public Integer registerUser(User newUser) {
        //stub method for now
        return null;
    }
}