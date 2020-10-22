package com.DAOs;

import com.Entities.Location;
import com.Entities.Meeting;
import com.Entities.User;

import java.util.Date;
import java.util.List;

public interface MeetingDAO extends DAO<Meeting>{
    public List<Meeting> getUpcomingMeetings();
    public List<Meeting> getByLocation(Location location);
    public List<Meeting> getByUserList(List<User> user);
    public void remove(Integer id);
    public List<Meeting> getConflictingUserSchedules(Integer candidateId, List<Integer> participantList, Date startTime, Date endTime);
    public List<Meeting> getConflictingLocations(Integer locationId, Date startTime, Date endTime);
    }
