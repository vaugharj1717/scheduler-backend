package com.services;

import com.App;
import com.DAOs.LocationDAO;
import com.DAOs.MeetingDAO;
import com.DAOs.ScheduleDAO;
import com.DAOs.UserDAO;
import com.Entities.Location;
import com.Entities.Meeting;
import com.Entities.Schedule;
import com.Entities.User;
import com.Entities.enumeration.MeetingType;
import com.Exceptions.ConflictingLocationException;
import com.Exceptions.ConflictingUserException;
import com.Services.MeetingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class MeetingServiceImplTest {

    @InjectMocks
    private MeetingServiceImpl meetingService;

    @Mock
    private MeetingDAO meetingDao;
    @Mock
    private UserDAO userDao;
    @Mock
    private ScheduleDAO scheduleDao;
    @Mock
    private LocationDAO locationDao;



    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateMeeting() throws Exception {
        //define return value of dependencies
        Meeting meeting = new Meeting();
        Date date1 = new Date();
        Date date2 = new Date();
        ArrayList<Integer> participantList = new ArrayList<Integer>();
        when(meetingDao.saveOrUpdate(meeting)).thenReturn(meeting);
        when(meetingDao.getConflictingLocations(1, date1, date2)).thenReturn(new ArrayList<Meeting>());
        when(meetingDao.getConflictingUserSchedules(1, participantList, date1, date2)).thenReturn(new ArrayList<Meeting>());
        User candidate = new User();
        candidate.setId(1);
        when(userDao.getByScheduleId(1)).thenReturn(candidate);
        when(scheduleDao.getById(1)).thenReturn(new Schedule());
        when(locationDao.getById(1)).thenReturn(new Location());

        //perform test
        Meeting result = meetingService.createMeeting(1, 1, date1, date2, MeetingType.MEET_FACULTY,
                new ArrayList<Boolean>(), new ArrayList<Boolean>(), participantList);
        assert meeting.equals(result);
    }

    @Test(expected = ConflictingLocationException.class)
    public void testCreateMeetingFailureLocationConflict() throws Exception {
        //define return value of dependencies
        Meeting meeting = new Meeting();
        Date date1 = new Date();
        Date date2 = new Date();
        ArrayList<Integer> participantList = new ArrayList<Integer>();
        when(meetingDao.saveOrUpdate(meeting)).thenReturn(meeting);
        ArrayList<Meeting> conflictingLocationMeetings = new ArrayList<>();
        conflictingLocationMeetings.add(new Meeting());
        when(meetingDao.getConflictingLocations(1, date1, date2)).thenReturn(conflictingLocationMeetings);
        when(meetingDao.getConflictingUserSchedules(1, participantList, date1, date2)).thenReturn(new ArrayList<Meeting>());
        User candidate = new User();
        candidate.setId(1);
        when(userDao.getByScheduleId(1)).thenReturn(candidate);
        when(scheduleDao.getById(1)).thenReturn(new Schedule());
        when(locationDao.getById(1)).thenReturn(new Location());

        //perform test
        Meeting result = meetingService.createMeeting(1, 1, date1, date2, MeetingType.MEET_FACULTY,
                new ArrayList<Boolean>(), new ArrayList<Boolean>(), participantList);
    }

    @Test(expected = ConflictingUserException.class)
    public void testCreateMeetingFailureUserConflict() throws Exception {
        //define return value of dependencies
        Meeting meeting = new Meeting();
        Date date1 = new Date();
        Date date2 = new Date();
        ArrayList<Integer> participantList = new ArrayList<Integer>();
        when(meetingDao.saveOrUpdate(meeting)).thenReturn(meeting);
        ArrayList<Meeting> conflictingUserMeetings = new ArrayList<>();
        conflictingUserMeetings.add(new Meeting());
        when(meetingDao.getConflictingLocations(1, date1, date2)).thenReturn(new ArrayList<>());
        when(meetingDao.getConflictingUserSchedules(1, participantList, date1, date2)).thenReturn(conflictingUserMeetings);
        User candidate = new User();
        candidate.setId(1);
        when(userDao.getByScheduleId(1)).thenReturn(candidate);
        when(scheduleDao.getById(1)).thenReturn(new Schedule());
        when(locationDao.getById(1)).thenReturn(new Location());

        //perform test
        Meeting result = meetingService.createMeeting(1, 1, date1, date2, MeetingType.MEET_FACULTY,
                new ArrayList<Boolean>(), new ArrayList<Boolean>(), participantList);
    }
}
