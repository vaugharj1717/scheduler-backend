package com.Services;

import com.DAOs.CandidacyDAO;
import com.DAOs.DepartmentDAO;
import com.DAOs.PositionDAO;
import com.DAOs.UserDAO;
import com.Entities.Candidacy;
import com.Entities.Department;
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
    @Autowired
    DepartmentDAO departmentDAO;

    @Override
    @Transactional
    public List<Position> getPositionsByDepartment(Integer id) {
        List<Position> positionList = positionDao.getPositionsByDepartment(id);
        return positionList;
    }

    @Override
    public List<Position> getAllPositions() {
        return positionDao.getAll();
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

    @Override
    @Transactional
    public void unassignCandidateFromPosition(Integer candidacyId){
        candidacyDao.remove(candidacyId);
    }

    @Transactional
    @Override
    public Position createPositionToDepartment(String positionName, Integer idDepartement) {
        Position postion = new Position();
        postion.setPositionName(positionName);
        Department department = departmentDAO.getById(idDepartement);
        if(department == null) return null;
        else {
            postion.setDepartment(department);
            return positionDao.saveOrUpdate(postion);
        }
    }

    @Transactional
    @Override
    public void removePosition(Integer positionId) {
        positionDao.remove(positionId);
    }
}
