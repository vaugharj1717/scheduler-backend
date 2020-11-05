package com.DAOs;

import com.Entities.Meeting;
import com.Entities.Participation;

import java.util.List;

public interface ParticipationDAO extends DAO<Participation>{
    public List<Participation> getAllParticipationByMeetingId(Integer meetingId);
}
