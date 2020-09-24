package com.Services;

import com.DAOs.CandidacyDAO;
import com.DAOs.PositionDAO;
import com.DAOs.UserDAO;
import com.Entities.Candidacy;
import com.Entities.Position;
import com.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDAO userDAO;

    @Autowired
    PositionDAO positionDAO;

    @Autowired
    CandidacyDAO candidacyDAO;

    @Transactional
    public List<User> getAllCandidates(){
        //no business logic
        List<User> userList = userDAO.getAllCandidates();
        return userList;
    }

    @Transactional
    public List<User> getAllParticipants() {
        return userDAO.getAllParticipants();
    }


    public Candidacy createCandidate(Integer positionId, String name, String email) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);

        Candidacy newCandidacy = new Candidacy();
        newCandidacy.setCandidate(newUser);
        Position position = positionDAO.getById(positionId);
        newCandidacy.setPosition(position);

        return candidacyDAO.saveOrUpdate(newCandidacy);
    }
}
