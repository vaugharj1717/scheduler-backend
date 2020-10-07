package com.Services;

import com.Entities.Schedule;

public interface ScheduleService {
    public Schedule getSchedule(Integer positionId);
    public void deleteSchedule(Integer scheduleId);
    public Schedule createSchedule(Integer candidacyId);
    public void deleteAllMeetingByScheduleId(Integer scheduleId);
}
