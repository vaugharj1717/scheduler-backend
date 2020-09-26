package com.SeedDatabase;

import com.DAOs.*;
import com.Entities.*;
import com.Entities.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;

@Component
public class DatabaseSeeder implements ApplicationListener<ContextRefreshedEvent>{

    @PersistenceContext
    EntityManager em;

    @Autowired
    CandidacyDAO candidacyDAO;
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
    @Autowired
    DepartmentDAO departmentDAO;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadData();
    }

    public void loadData(){

        //create Department
        Department department = new Department();
        department.setDepartmentName("testDepartmentName");
        department = departmentDAO.saveOrUpdate(department);

        //create Position
        Position position = new Position();
        position.setPositionName("testPositionName");
        position.setDepartment(department);
        position = positionDAO.saveOrUpdate(position);


        //create Candidate
        User candidate = new User();
        candidate.setUsername("testUsername");
        candidate.setPassword("testPassword");
        candidate.setRole(Role.CANDIDATE);
        candidate.setName("testName");
        candidate.setEmail("testEmail");
        candidate.setPhone("testPhone");

        //create Candidacy
        Candidacy candidacy = new Candidacy();
        em.persist(candidacy);
        candidacy.setCandidate(candidate);
        candidacy.setPosition(position);
        candidacy = candidacyDAO.saveOrUpdate(candidacy);


        //create Schedule
        Schedule schedule = new Schedule();
        em.persist(schedule);
        schedule.setCandidacy(candidacy);
        schedule = scheduleDAO.saveOrUpdate(schedule);

        //create Location
        Location location = new Location();
        location.setRoomNumber(1);
        location.setBuildingName("testBuildingName");
        location = locationDAO.saveOrUpdate(location);

        //create participant
        User participant = new User();
        em.persist(participant);
        participant.setUsername("testUsername");
        participant.setPassword("testPassword");
        participant.setRole(Role.DEPARTMENT_ADMIN);
        participant.setName("testName");
        participant.setEmail("testEmail");
        participant.setPhone("testPhone");
        participant.setDepartment(department);
        participant = userDAO.saveOrUpdate(participant);

        //create Meeting
        Meeting meeting = new Meeting();
        em.persist(meeting);
        meeting.setMeetingType("testingMeetingType");
        meeting.setLocation(location);
        Date newDate1 = new Date(5);
        Date newDate2 = new Date(6);
        meeting.setStartTime(newDate1);
        meeting.setEndTime(newDate2);
        meeting.setSchedule(schedule);
        meeting = meetingDAO.saveOrUpdate(meeting);

        //create participation
        Participation participation = new Participation();
        em.persist(participation);
        participation.setParticipant(participant);
        participation.setMeeting(meeting);
        participation.setAlert(true);
        participation.setAlertType("email");
        participation.setCanLeaveFeedback(true);
        participation.setCanViewFeedback(true);
        participation.setCanMakeDecision(true);
        participation = participationDAO.saveOrUpdate(participation);
//
//        //manual testing
//        List<Meeting> meetingListByLocation = meetingDAO.getByLocation(locationDAO.getById(savedLocation.getId()));
//        List<User> userList = new ArrayList<User>();
//        userList.add(userDAO.getById(savedCandidate.getId()));
//        userList.add(userDAO.getById(savedParticipant.getId()));
//        List<Meeting> meetingListByUser = meetingDAO.getByUserList(userList);
    }



}

