package com.Services;

import com.Entities.Meeting;
import com.Entities.enumeration.MeetingType;

import java.util.Date;
import java.util.List;

public interface MeetingService {
    public Meeting getMeeting(Integer meetingId);
    public List<Meeting> getUpcomingMeetings();
    public List<Meeting> getUpcomingMeetingsById(Integer userId);
    public List<Meeting> getPastMeetings();
    public List<Meeting> getPastMeetingsById(Integer userId);
    public Meeting createMeeting(Integer scheduleId, Integer locationId, Date startTime, Date endTime, MeetingType meetingType,
                                 List<Boolean> canViewFeedbackList, List<Boolean> canLeaveFeedbackList, List<Integer> participantList)
                                 throws Exception;
    public void deleteMeeting(Integer id);
}
