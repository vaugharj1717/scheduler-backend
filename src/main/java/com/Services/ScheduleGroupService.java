package com.Services;

import com.Entities.ScheduleGroup;

import java.util.List;

public interface ScheduleGroupService {
    public List<ScheduleGroup> getAll();
    public ScheduleGroup createScheduleGroup(Integer positionId);
}
