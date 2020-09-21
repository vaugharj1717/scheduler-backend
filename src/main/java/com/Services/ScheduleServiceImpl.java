package com.Services;

import com.DAOs.PositionDAO;
import com.DAOs.ScheduleDAO;
import com.DAOs.ScheduleGroupDAO;
import com.Entities.Position;
import com.Entities.Schedule;
import com.Entities.ScheduleGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Autowired
    ScheduleDAO scheduleDAO;

    @Transactional
    public Schedule getSchedule(Integer scheduleId){
        Schedule schedule = scheduleDAO.getById(scheduleId);
        return schedule;
    }
}
