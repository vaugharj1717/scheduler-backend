package com.Services;

import com.Entities.ScheduleGroup;

import java.util.List;

public interface ScheduleService {
    public List<ScheduleGroup> getAllSchedules();
    public ScheduleGroup createNewScheduleGroup(Integer positionId);
}
