package com.Services;

import com.Entities.Candidacy;
import com.Entities.User;
import com.Entities.UserFile;
import com.Entities.UserMessage;
import com.Entities.enumeration.Role;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    public List<User> getAllCandidates();
    public List<User> getAllParticipants();
    public Candidacy createCandidate(Integer positionId, String name, String email);
    public void adminControlDeleteUser(Integer userId, String loggedUserEmail) throws Exception;
    public User adminControlsCreateUser(String name, String email, String role);
    public UserFile storeUserFile(MultipartFile file, Integer userId) throws IOException;
    public Resource loadUserFileAsResource(Integer fileId) throws IOException;
    public void deleteFile(Integer fileId) throws IOException;
    public List<UserFile> getUserFiles(Integer userId);
    public void changeRole(Role role, Integer userId);
    public List<User> getAllUsers(String loggedUserEmail);
    public List<User> getCandidatesAndParticipants(String loggedUserEmail);
    public List<UserMessage> getMessages(Integer userId, boolean isViewing);
    public UserMessage sendMessage(Integer senderId, Integer recipientId, String message) throws Exception;
    public void changePassword(String logUser, Integer userId, String oldPassword, String newPassword, String newPassword2) throws Exception;
    public User getUserWithDepart(Integer userId);
}
