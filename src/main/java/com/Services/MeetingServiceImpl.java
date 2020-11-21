package com.Services;

import com.DAOs.LocationDAO;
import com.DAOs.MeetingDAO;
import com.DAOs.ScheduleDAO;
import com.DAOs.UserDAO;
import com.Entities.*;
import com.Entities.enumeration.MeetingType;
import com.Exceptions.ConflictingLocationException;
import com.Exceptions.ConflictingUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<Meeting> getUpcomingMeetings(){
        List<Meeting> meetings = meetingDAO.getUpcomingMeetings();
        return meetings;
    }

    @Transactional
    public List<Meeting> getPastMeetings(){
        List<Meeting> meetings = meetingDAO.getPastMeetings();
        return meetings;
    }

    @Transactional
    public List<Meeting> getUpcomingMeetingsById(Integer userId){
        List<Meeting> meetings = meetingDAO.getUpcomingMeetingsById(userId);
        return meetings;
    }
    @Transactional
    public List<Meeting> getPastMeetingsById(Integer userId){
        List<Meeting> meetings = meetingDAO.getPastMeetingsById(userId);
        return meetings;
    }

    @Transactional
    public Meeting createMeeting(Integer scheduleId, Integer locationId, Date startTime, Date endTime, MeetingType meetingType,
                                 List<Boolean> canViewFeedbackList, List<Boolean> canLeaveFeedbackList, List<Integer> participantList)
                                 throws Exception{

        //Check for scheduling conflicts
        //Check for conflict with participant or candidate's schedules
        User candidate = userDAO.getByScheduleId(scheduleId);
        List<Meeting> conflictingMeetingList = meetingDAO.getConflictingUserSchedules(candidate.getId(), participantList, startTime, endTime);
        if(conflictingMeetingList.size() != 0){
            throw new ConflictingUserException("A user has a meeting during this time");
        }

        //Check for conflict with location availability
        List<Meeting> conflictingMeetingList2 = meetingDAO.getConflictingLocations(locationId, startTime, endTime);
        if(conflictingMeetingList2.size() != 0){
            throw new ConflictingLocationException("This location is not available at the specified time");
        }

        //object to save
        Meeting newMeeting = new Meeting();

        //retrieve schedule and location and check that values were returned, else return null (failure)
        Schedule schedule = scheduleDAO.getById(scheduleId);
        Location location = locationDAO.getById(locationId);
        if(schedule == null || location == null) throw new IllegalStateException();

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
    @Transactional
    public void deleteMeeting(Integer id) {
        meetingDAO.remove(id);
    }
}
