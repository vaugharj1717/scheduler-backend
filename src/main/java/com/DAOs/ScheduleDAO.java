package com.DAOs;

import com.Entities.Schedule;

public interface ScheduleDAO extends DAO<Schedule>{
    public Schedule getById(Integer id);
    public Schedule saveOrUpdate(Schedule schedule);
    public void remove(Integer id);
    public void deleteAllMeetingByScheduleId(Integer scheduleId);
}
