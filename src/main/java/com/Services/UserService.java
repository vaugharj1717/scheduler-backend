package com.Services;

import com.Entities.User;

import java.util.List;

public interface UserService {

    public User getUserById(Integer id);

    public void deleteUserById(Integer id);

    public Integer registerUser(User newUser);

    public List<User> getAllPaticipants();
}
