package com.Services;

import com.DAOs.UserDAO;
import com.Entities.User;

import java.util.List;

public interface UserService {

    public void setUserDAO(UserDAO userDAO);

    public Integer registerUser(User newUser);

    public List<User> getAllParticipants();
}
