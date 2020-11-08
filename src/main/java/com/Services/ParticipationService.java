package com.Services;

import com.Entities.Participation;

import java.util.List;

public interface ParticipationService {
    void setFeedback (Integer participationId, String feedback) throws Exception;
    List<Participation> getAllParticipationByMeetingId(Integer meetingId);
    void patchParticipantAlert(Integer participationId, Boolean alert);
}
