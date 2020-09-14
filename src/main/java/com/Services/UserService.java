package com.Services;

import com.Entities.User;

public interface UserService {

    public User getUserById(Integer id);

    public void deleteUserById(Integer id);

    public Integer registerUser(User newUser);
}
