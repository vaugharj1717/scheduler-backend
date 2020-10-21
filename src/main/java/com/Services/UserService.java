package com.Services;

import com.Entities.Candidacy;
import com.Entities.User;
import com.Entities.UserFile;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    public List<User> getAllCandidates();
    public List<User> getAllParticipants();
    public Candidacy createCandidate(Integer positionId, String name, String email);
    public void adminControlDeleteUser(Integer userId);
    public User adminControlsCreateUser(String name, String email, String role);
    public UserFile storeUserFile(MultipartFile file, Integer userId) throws IOException;
    public Resource loadUserFileAsResource(Integer fileId) throws IOException;
}
