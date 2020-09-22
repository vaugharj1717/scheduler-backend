package com.Services;

import com.Entities.Schedule;
import com.Entities.ScheduleGroup;

import java.util.List;

public interface ScheduleService {
    public Schedule getSchedule(Integer positionId);
    public Schedule deleteSchedule(Integer scheduleId);
}
