package com.Services;

import com.DAOs.PositionDAO;
import com.Entities.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService{

    @Autowired
    PositionDAO positionDao;

    @Override
    public List<Position> getPositionsByDepartement(Integer id) {

        List<Position> positionList = positionDao.getPositionsByDepartement(id);
        return positionList;
    }
}
