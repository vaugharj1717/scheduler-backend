package com.Services;

import com.DAOs.CandidacyDAO;
import com.DAOs.ScheduleDAO;
import com.Entities.Candidacy;
import com.Entities.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Autowired
    ScheduleDAO scheduleDAO;

    @Autowired
    CandidacyDAO candidacyDAO;

    @Transactional
    public Schedule getSchedule(Integer scheduleId){
        Schedule schedule = scheduleDAO.getById(scheduleId);
        return schedule;
    }

    @Transactional
    public void deleteSchedule(Integer scheduleId){
        scheduleDAO.remove(scheduleId);
    }

    @Transactional
    public Schedule createSchedule(Integer candidacyId){
        Candidacy newCandidacy = candidacyDAO.getById(candidacyId);
        Schedule newSchedule = new Schedule();
        newSchedule.setCandidacy(newCandidacy);
        return scheduleDAO.saveOrUpdate(newSchedule);

    }

    @Transactional
    public void deleteAllMeetingByScheduleId(Integer scheduleId) {
        scheduleDAO.deleteAllMeetingByScheduleId(scheduleId);
    }
}
