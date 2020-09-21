package com.Services;

import com.DAOs.UserDAO;
import com.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAllParticipants() {
        return userDAO.getAllParticipants();
    }

    @Override
    public Integer registerUser(User newUser) {
        userDAO.saveOrUpdate(newUser);
        return null;
    }
}
