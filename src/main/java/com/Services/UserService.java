package com.Services;

import com.Entities.Candidacy;
import com.Entities.User;

import java.util.List;

public interface UserService {
    public List<User> getAllCandidates();
    public List<User> getAllParticipants();
    public Candidacy createCandidate(Integer positionId, String name, String email);
    public void adminControlDeleteUser(Integer userId);
    public User adminControlsCreateUser(String name, String email, String role);
}
