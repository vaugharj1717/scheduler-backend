package com.DAOs;

import com.Entities.User;

import java.util.List;

public interface UserDAO extends DAO<User>{
    public List<User> getAllCandidates();
    public List<User> getAllParticipants();
    public User getByScheduleId(Integer scheduleId);
}
