package com.DAOs;

import com.Entities.User;

import java.util.List;

public interface UserDAO {

    public User saveOrUpdate(User user);

    public List<User> getAllParticipants();
}
