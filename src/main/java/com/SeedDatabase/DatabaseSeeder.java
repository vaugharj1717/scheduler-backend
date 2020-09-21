package com.SeedDatabase;

import com.DAOs.*;
import com.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Component
public class DatabaseSeeder implements ApplicationListener<ContextRefreshedEvent>{

    @PersistenceContext
    EntityManager em;

    @Autowired
    ScheduleGroupDAO scheduleGroupDAO;
    @Autowired
    ScheduleDAO scheduleDAO;
    @Autowired
    MeetingDAO meetingDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    PositionDAO positionDAO;
    @Autowired
    LocationDAO locationDAO;
    @Autowired
    ParticipationDAO participationDAO;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadData();
    }

    public void loadData(){

        //create Position
        Position position = new Position();
        position.setPositionName("testPositionName");
        Position savedPosition = positionDAO.saveOrUpdate(position);

        //create Candidate
        User candidate = new User();
        candidate.setUsername("testUsername");
        candidate.setPassword("testPassword");
        candidate.setRole("candidate");
        candidate.setFirstName("testFirstName");
        candidate.setLastName("testLastName");
        candidate.setEmail("testEmail");
        candidate.setPhone("testPhone");
        User savedCandidate = userDAO.saveOrUpdate(candidate);



        //create ScheduleGroup
        Position retrievedPosition = positionDAO.getById(savedPosition.getId());
        ScheduleGroup scheduleGroup = new ScheduleGroup();
        scheduleGroup.setPosition(retrievedPosition);
        ScheduleGroup savedScheduleGroup = scheduleGroupDAO.saveOrUpdate(scheduleGroup);

        //create Schedule
        ScheduleGroup retrievedScheduleGroup = scheduleGroupDAO.getById(savedScheduleGroup.getId());
        User retrievedCandidate = userDAO.getById(savedCandidate.getId());
        Schedule schedule = new Schedule();
        schedule.setScheduleGroup(retrievedScheduleGroup);
        schedule.setCandidate(retrievedCandidate);
        Schedule savedSchedule = scheduleDAO.saveOrUpdate(schedule);

        //create Location
        Location location = new Location();
        location.setRoomNumber(1);
        location.setBuildingName("testBuildingName");
        Location savedLocation = locationDAO.saveOrUpdate(location);

        //create Meeting
        Meeting meeting = new Meeting();
        meeting.setMeetingType("testingMeetingType");
        meeting.setLocation(locationDAO.getById(savedLocation.getId()));
        meeting.setSchedule(scheduleDAO.getById(savedSchedule.getId()));
        Meeting savedMeeting = meetingDAO.saveOrUpdate(meeting);

        //create participant
        User participant = new User();
        participant.setUsername("testUsername");
        participant.setPassword("testPassword");
        participant.setRole("participant");
        participant.setFirstName("testFirstName");
        participant.setLastName("testLastName");
        participant.setEmail("testEmail");
        participant.setPhone("testPhone");
        User savedParticipant = userDAO.saveOrUpdate(participant);

        //create participation
        Participation participation = new Participation();
        User retrievedParticipant = userDAO.getById(savedParticipant.getId());
        Meeting retrievedMeeting = meetingDAO.getById(savedMeeting.getId());
        participation.setUser(retrievedParticipant);
        participation.setMeeting(retrievedMeeting);
        participation.setAlert(true);
        participation.setAlertType("email");
        participation.setCanLeaveFeedback(true);
        participation.setCanViewFeedback(true);
        participation.setCanMakeDecision(true);
        Participation savedParticipation = participationDAO.saveOrUpdate(participation);
    }



}

