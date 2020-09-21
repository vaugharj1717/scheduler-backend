package com.DAOs;

import com.Entities.User;

import java.util.List;

public interface UserDAO extends DAO<User>{
    List<User> getAllParticipants();
}
