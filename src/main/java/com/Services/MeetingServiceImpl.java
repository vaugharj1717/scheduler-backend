package com.Services;

import com.DAOs.LocationDAO;
import com.DAOs.MeetingDAO;
import com.DAOs.ScheduleDAO;
import com.Entities.Location;
import com.Entities.Meeting;
import com.Entities.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeetingServiceImpl implements MeetingService{
    @Autowired
    MeetingDAO meetingDAO;

    @Autowired
    ScheduleDAO scheduleDAO;

    @Autowired
    LocationDAO locationDAO;

    @Transactional
    public Meeting getMeeting(Integer meetingId){
        Meeting meeting = meetingDAO.getById(meetingId);
        return meeting;
    }

    @Transactional
    //TODO: parse startTime/endTime and save them as well
    public Meeting createMeeting(Integer scheduleId, Integer locationId, String startTime, String endTime){
        Schedule schedule = scheduleDAO.getById(scheduleId);
        Location location = locationDAO.getById(locationId);
        if(schedule == null || location == null) return null;
        else{
            Meeting newMeeting = new Meeting();
            newMeeting.setSchedule(schedule);
            newMeeting.setLocation(location);
            Meeting savedMeeting = meetingDAO.saveOrUpdate(newMeeting);
            return savedMeeting;
        }

    }

    @Override
    public void deleteMeeting(Integer id) {
        meetingDAO.remove(id);
    }
}
