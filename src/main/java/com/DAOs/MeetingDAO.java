package com.DAOs;

import com.Entities.Location;
import com.Entities.Meeting;
import com.Entities.User;

import java.util.List;

public interface MeetingDAO extends DAO<Meeting>{
    public List<Meeting> getByLocation(Location location);
    public List<Meeting> getByUserList(List<User> user);
}
