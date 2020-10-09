package com.SeedDatabase;

import com.DAOs.*;
import com.Entities.*;
import com.Entities.enumeration.MeetingType;
import com.Entities.enumeration.Role;
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
        department.setDepartmentName("CS");
        department = departmentDAO.saveOrUpdate(department);

        Department department2 = new Department();
        department2.setDepartmentName("Business");
        department2 = departmentDAO.saveOrUpdate(department2);

        Department department3 = new Department();
        department3.setDepartmentName("Mathematics");
        department3 = departmentDAO.saveOrUpdate(department3);

        //create Position
        Position position = new Position();
        position.setPositionName("Instructor");
        position.setDepartment(department);
        position = positionDAO.saveOrUpdate(position);


        //create Candidate
        User candidate = new User();
        candidate.setUsername("testUsername");
        candidate.setPassword("testPassword");
        candidate.setRole(Role.CANDIDATE);
        candidate.setName("John Doe");
        candidate.setEmail("jdoe19@gmail.com");
        candidate.setPhone("testPhone");

        User candidate2 = new User();
        candidate2.setUsername("testUsername");
        candidate2.setPassword("testPassword");
        candidate2.setRole(Role.CANDIDATE);
        candidate2.setName("Susan Jones");
        candidate2.setEmail("sjones44@gmail.com");
        candidate2.setPhone("testPhone");
        candidate2 = userDAO.saveOrUpdate(candidate2);

        User candidate3 = new User();
        candidate3.setUsername("testUsername");
        candidate3.setPassword("testPassword");
        candidate3.setRole(Role.CANDIDATE);
        candidate3.setName("Russel Hapsburg");
        candidate3.setEmail("rhapsburg07@gmail.com");
        candidate3.setPhone("testPhone");
        candidate2 = userDAO.saveOrUpdate(candidate3);

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

        User participant2 = new User();
        em.persist(participant2);
        participant2.setUsername("testUsername2");
        participant2.setPassword("testPassword2");
        participant2.setRole(Role.PARTICIPANT);
        participant2.setName("testName2");
        participant2.setEmail("testEmail2");
        participant2.setPhone("testPhone2");
        participant2.setDepartment(department);
        participant2 = userDAO.saveOrUpdate(participant2);

        //create Meeting
        Meeting meeting = new Meeting();
        em.persist(meeting);
        meeting.setMeetingType(MeetingType.MEET_FACULTY);
        meeting.setLocation(location);
        Date newDate1 = new Date(5);
        Date newDate2 = new Date(6);
        meeting.setStartTime(newDate1);
        meeting.setEndTime(newDate2);
        meeting.setSchedule(schedule);
        meeting = meetingDAO.saveOrUpdate(meeting);

        Meeting meeting2 = new Meeting();
        em.persist(meeting2);
        meeting2.setMeetingType(MeetingType.MEET_FACULTY);
        meeting2.setLocation(location);
        Date newDate3 = new Date(5);
        Date newDate4 = new Date(6);
        meeting2.setStartTime(newDate3);
        meeting2.setEndTime(newDate4);
        meeting2.setSchedule(schedule);
        meeting2 = meetingDAO.saveOrUpdate(meeting2);

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

        Participation participation2 = new Participation();
        em.persist(participation2);
        participation2.setParticipant(participant);
        participation2.setMeeting(meeting2);
        participation2.setAlert(true);
        participation2.setAlertType("email");
        participation2.setCanLeaveFeedback(true);
        participation2.setCanViewFeedback(true);
        participation2.setCanMakeDecision(true);
        participation2 = participationDAO.saveOrUpdate(participation2);

        Participation participation3 = new Participation();
        em.persist(participation3);
        participation3.setParticipant(participant);
        participation3.setMeeting(meeting);
        participation3.setAlert(true);
        participation3.setAlertType("email");
        participation3.setCanLeaveFeedback(true);
        participation3.setCanViewFeedback(true);
        participation3.setCanMakeDecision(true);
        participation3 = participationDAO.saveOrUpdate(participation3);
//
//        //manual testing
//        List<Meeting> meetingListByLocation = meetingDAO.getByLocation(locationDAO.getById(savedLocation.getId()));
//        List<User> userList = new ArrayList<User>();
//        userList.add(userDAO.getById(savedCandidate.getId()));
//        userList.add(userDAO.getById(savedParticipant.getId()));
//        List<Meeting> meetingListByUser = meetingDAO.getByUserList(userList);
    }



}

