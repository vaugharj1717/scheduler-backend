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
public class PositionServiceImpl implements PositionService{

    @Autowired
    PositionDAO positionDao;
    @Autowired
    UserDAO userDao;
    @Autowired
    CandidacyDAO candidacyDao;

    @Override
    @Transactional
    public List<Position> getPositionsByDepartment(Integer id) {
        List<Position> positionList = positionDao.getPositionsByDepartment(id);
        return positionList;
    }

    @Override
    @Transactional
    public Candidacy assignCandidateToPosition(Integer positionId, Integer candidateId){
        Position position = positionDao.getById(positionId);
        User candidate = userDao.getById(candidateId);
        Candidacy newCandidacy = new Candidacy();
        newCandidacy.setCandidate(candidate);
        newCandidacy.setPosition(position);
        return candidacyDao.saveOrUpdate(newCandidacy);

    }
}
