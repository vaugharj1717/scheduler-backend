package com.Services;

import com.Entities.User;

import java.util.List;

public interface UserService {
    public List<User> getAllCandidates();
    public List<User> getAllParticipants();
}
