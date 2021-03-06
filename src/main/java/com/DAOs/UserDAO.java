package com.DAOs;

import com.Entities.User;
import com.Entities.UserFile;
import com.Entities.UserMessage;
import com.Entities.enumeration.Role;

import java.util.List;

public interface UserDAO extends DAO<User>{
    public List<User> getAllCandidates();
    public List<User> getAllParticipants();
    public User getByScheduleId(Integer scheduleId);
    public User findByEmail(String email);
    public UserFile addUserFile(UserFile userFile);
    public UserFile getUserFileById(Integer userFileId);
    public List<UserFile> getUserFilesByUserId(Integer userId);
    public void changeRole(Role role, Integer userId);
    public List<User> getAllBesidesSuperAdminsAndSelf(String email);
    public List<User> getCandidatesAndParticipants(String userEmail);
    public void markMessagesAsSeen(Integer recipientId);
    public List<UserMessage> getMessagesByUserId(Integer userId);
    public UserMessage saveMessage(UserMessage message);
    public void removeFile(Integer id);
    public User getUserWithDepart(Integer userId);
    public void setCandidateAlert(Integer userId, Boolean val);
}
