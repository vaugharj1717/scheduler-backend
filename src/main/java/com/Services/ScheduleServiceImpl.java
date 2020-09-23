package com.Services;

import com.DAOs.ScheduleDAO;
import com.Entities.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Autowired
    ScheduleDAO scheduleDAO;

    @Transactional
    public Schedule getSchedule(Integer scheduleId){
        Schedule schedule = scheduleDAO.getById(scheduleId);
        return schedule;
    }

    @Transactional
    public void deleteSchedule(Integer scheduleId){
        scheduleDAO.remove(scheduleId);
    }
}
