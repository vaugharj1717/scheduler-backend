package com.Services;

import com.DAOs.PositionDAO;
import com.DAOs.ScheduleDAO;
import com.Entities.Position;
import com.Entities.ScheduleGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    ScheduleDAO scheduleDAO;
    @Autowired
    PositionDAO positionDAO;

    public List<ScheduleGroup> getAllSchedules(){
        List<ScheduleGroup> scheduleGroupList = scheduleDAO.getAllSchedules();
        return scheduleGroupList;
    }

    @Transactional
    public ScheduleGroup createNewScheduleGroup(Integer positionId){
        System.out.println("In Service");
        Position position = positionDAO.getById(positionId);
        ScheduleGroup scheduleGroup = scheduleDAO.createScheduleGroup(position);
        return scheduleGroup;
    }
}
