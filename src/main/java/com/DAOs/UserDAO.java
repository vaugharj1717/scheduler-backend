package com.DAOs;

import com.Entities.User;
import com.Entities.UserFile;

import java.util.List;

public interface UserDAO extends DAO<User>{
    public List<User> getAllCandidates();
    public List<User> getAllParticipants();
    public User getByScheduleId(Integer scheduleId);
    public User findByEmail(String email);
    public UserFile addUserFile(UserFile userFile);
    public UserFile getUserFileById(Integer userFileId);
}
