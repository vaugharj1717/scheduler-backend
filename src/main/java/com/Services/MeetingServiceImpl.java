package com.Services;

import com.DAOs.MeetingDAO;
import com.Entities.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingServiceImpl implements MeetingService{
    @Autowired
    MeetingDAO meetingDAO;
    public Meeting getMeeting(Integer meetingId){
        Meeting meeting = meetingDAO.getById(meetingId);
        return meeting;
    }
}
