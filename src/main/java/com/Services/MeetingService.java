package com.Services;

import com.Entities.Meeting;
import com.Entities.ScheduleGroup;

import java.util.List;

public interface MeetingService {
    public Meeting getMeeting(Integer meetingId);
    public Meeting createMeeting(Integer scheduleId, Integer locationId, String startTime, String endTime);
}
