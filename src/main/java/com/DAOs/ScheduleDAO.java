package com.DAOs;

import com.Entities.Position;
import com.Entities.ScheduleGroup;

import java.util.List;

public interface ScheduleDAO {
    public List<ScheduleGroup> getAllSchedules();
    public ScheduleGroup createScheduleGroup(Position position);
}
