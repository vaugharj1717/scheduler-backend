package com.Services;

import com.Entities.Meeting;
import com.Entities.enumeration.MeetingType;

import java.util.Date;
import java.util.List;

public interface MeetingService {
    public Meeting getMeeting(Integer meetingId);
    public Meeting createMeeting(Integer scheduleId, Integer locationId, Date startTime, Date endTime, MeetingType meetingType,
                                 List<Boolean> canViewFeedbackList, List<Boolean> canLeaveFeedbackList, List<Integer> participantList);
    public void deleteMeeting(Integer id);
}
