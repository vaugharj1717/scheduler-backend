package com.Services;

import com.DAOs.PositionDAO;
import com.DAOs.CandidacyDAO;
import com.Entities.Position;
import com.Entities.Candidacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleGroupServiceImpl implements ScheduleGroupService{
    @Autowired
    CandidacyDAO candidacyDAO;
    @Autowired
    PositionDAO positionDAO;

    @Transactional
    public List<Candidacy> getAll(){
        List<Candidacy> candidacyList = candidacyDAO.getAll();
        return candidacyList;
    }

    @Transactional
    public Candidacy createScheduleGroup(Integer positionId){
        Position position = positionDAO.getById(positionId);
        if(position == null) return null;
        else{
            Candidacy newCandidacy = new Candidacy();
            newCandidacy.setPosition(position);
            Candidacy savedCandidacy = candidacyDAO.saveOrUpdate(newCandidacy);
            return savedCandidacy;
        }
    }
}
