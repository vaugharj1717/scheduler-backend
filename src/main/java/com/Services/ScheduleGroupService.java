package com.Services;

import com.Entities.Candidacy;

import java.util.List;

public interface ScheduleGroupService {
    public List<Candidacy> getAll();
    public Candidacy createScheduleGroup(Integer positionId);
}
