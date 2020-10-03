package com.Services;

import com.Entities.Meeting;

public interface MeetingService {
    public Meeting getMeeting(Integer meetingId);
    public Meeting createMeeting(Integer scheduleId, Integer locationId, String startTime, String endTime);
    public void deleteMeeting(Integer id);
}
