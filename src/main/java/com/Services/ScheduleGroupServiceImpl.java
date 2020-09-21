package com.Services;

import com.DAOs.PositionDAO;
import com.DAOs.ScheduleGroupDAO;
import com.Entities.Position;
import com.Entities.ScheduleGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleGroupServiceImpl implements ScheduleGroupService{
    @Autowired
    ScheduleGroupDAO scheduleGroupDAO;
    @Autowired
    PositionDAO positionDAO;

    @Transactional
    public List<ScheduleGroup> getAll(){
        List<ScheduleGroup> scheduleGroupList = scheduleGroupDAO.getAll();
        return scheduleGroupList;
    }

    @Transactional
    public ScheduleGroup createScheduleGroup(Integer positionId){
        Position position = positionDAO.getById(positionId);
        if(position == null) return null;
        else{
            ScheduleGroup newScheduleGroup = new ScheduleGroup();
            newScheduleGroup.setPosition(position);
            ScheduleGroup savedScheduleGroup = scheduleGroupDAO.saveOrUpdate(newScheduleGroup);
            return savedScheduleGroup;
        }
    }
}
