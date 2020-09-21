package com.Services;

import com.DAOs.PositionDAO;
import com.Entities.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService{

    @Autowired
    PositionDAO positionDao;

    @Override
    @Transactional
    public List<Position> getPositionsByDepartment(Integer id) {
        List<Position> positionList = positionDao.getPositionsByDepartment(id);
        return positionList;
    }
}
