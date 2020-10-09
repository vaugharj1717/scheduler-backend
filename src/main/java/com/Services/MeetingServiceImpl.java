package com.Services;

import com.DAOs.LocationDAO;
import com.DAOs.MeetingDAO;
import com.DAOs.ScheduleDAO;
import com.DAOs.UserDAO;
import com.Entities.*;
import com.Entities.enumeration.MeetingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService{
    @Autowired
    MeetingDAO meetingDAO;

    @Autowired
    ScheduleDAO scheduleDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    UserDAO userDAO;

    @Transactional
    public Meeting getMeeting(Integer meetingId){
        Meeting meeting = meetingDAO.getById(meetingId);
        return meeting;
    }

    @Transactional
    public Meeting createMeeting(Integer scheduleId, Integer locationId, Date startTime, Date endTime, MeetingType meetingType,
                                 List<Boolean> canViewFeedbackList, List<Boolean> canLeaveFeedbackList, List<Integer> participantList){

        //object to save
        Meeting newMeeting = new Meeting();

        //retrieve schedule and location and check that values were returned, else return null (failure)
        Schedule schedule = scheduleDAO.getById(scheduleId);
        Location location = locationDAO.getById(locationId);
        if(schedule == null || location == null) return null;

        //retrieve each participant and create participation object for each
        //then attach participation to new meeting
        for (int i = 0; i < participantList.size(); i++) {
            User participant = userDAO.getById(participantList.get(i));
            if (participant == null) return null;
            Participation newParticipation = new Participation();
            newParticipation.setParticipant(participant);
            newParticipation.setCanViewFeedback(canViewFeedbackList.get(i));
            newParticipation.setCanLeaveFeedback(canLeaveFeedbackList.get(i));
            newParticipation.setTransientId(i);
            newMeeting.addParticipation(newParticipation);
        }

        //build new meeting object
        newMeeting.setSchedule(schedule);
        newMeeting.setLocation(location);
        newMeeting.setStartTime(startTime);
        newMeeting.setEndTime(endTime);
        newMeeting.setMeetingType(meetingType);

        //save object and return
        Meeting savedMeeting = meetingDAO.saveOrUpdate(newMeeting);
        return savedMeeting;
    }

    @Override
    public void deleteMeeting(Integer id) {
        meetingDAO.remove(id);
    }
}
