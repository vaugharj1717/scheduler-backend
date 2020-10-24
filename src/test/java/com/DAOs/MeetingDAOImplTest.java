package com.DAOs;

import com.App;
import com.Entities.*;
import com.Entities.Meeting;
import com.Entities.Meeting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class MeetingDAOImplTest {

    @Autowired
    private MeetingDAO meetingDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LocationDAO locationDAO;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        List<Meeting> meetings = meetingDAO.getAll();
        assert meetings.size() == 2;
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Transactional
    public void testRemove() throws Exception {
        List<Meeting> meetings = (List<Meeting>) meetingDAO.getAll();
        meetingDAO.remove(meetings.get(0).getId());

        em.flush();
        for(Meeting Meeting : meetings) em.detach(Meeting);

        Meeting meeting = meetingDAO.getById(meetings.get(0).getId());
    }

    @Test
    @Transactional
    public void testSaveOrUpdate() throws Exception {
        Meeting meeting = new Meeting();
        meeting = meetingDAO.saveOrUpdate(meeting);

        em.flush();
        em.detach(meeting);

        Meeting resultMeeting = meetingDAO.getById(meeting.getId());
        assert resultMeeting.equals(meeting);
    }
    
    @Test
    @Transactional
    public void testGetById() throws Exception {
        List<Meeting> meetings = (List<Meeting>) meetingDAO.getAll();
        Meeting meeting = meetingDAO.getById(meetings.get(0).getId());
        assert meeting.equals(meetings.get(0));
    }

    @Test
    @Transactional
    public void testGetUpcomingMeetings() throws Exception {
        List<Meeting> meetings = (List<Meeting>) meetingDAO.getUpcomingMeetings();
        List<Meeting> meetingList = meetingDAO.getUpcomingMeetings();
        assert meetingList.size() == 1;
        assert meetingList.get(0).getStartTime().getTime() > new Date().getTime();
    }

    @Test
    @Transactional
    public void testGetPastMeetings() throws Exception {
        List<Meeting> meetings = (List<Meeting>) meetingDAO.getUpcomingMeetings();
        List<Meeting> meetingList = meetingDAO.getPastMeetings();
        assert meetingList.size() == 1;
        assert meetingList.get(0).getEndTime().getTime() < new Date().getTime();
    }

    @Test
    @Transactional
    public void testGetConflictingUserSchedules() throws Exception{
        User candidate = userDAO.getAllCandidates().get(0);
        User participant = userDAO.getAllParticipants().get(0);
        List<Integer> participantList = new ArrayList<>();
        participantList.add(participant.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse("1970-01-01 12:00:00");
        Date endTime = sdf.parse("2200-01-01 13:00:00");
        List<Meeting> meetingList = meetingDAO.getConflictingUserSchedules(candidate.getId(), participantList, startTime, endTime);
        assert meetingList.size() == 2;
    }

    @Test
    @Transactional
    public void testGetConflictingLocations() throws Exception{
        Location location = locationDAO.getAll().get(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse("1970-01-01 12:00:00");
        Date endTime = sdf.parse("2200-01-01 13:00:00");
        List<Meeting> meetingList = meetingDAO.getConflictingLocations(location.getId(), startTime, endTime);
        assert meetingList.size() == 2;
    }


}
