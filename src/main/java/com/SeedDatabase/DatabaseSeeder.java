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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        /*loadLocations();
        loadDepartments();
        loadCandidates();
        loadParticipants();
        loadPositions();
        loadCandidacies();
        loadMeetings();*/

        loadData();
    }

    @Transactional
    //Load 2 locations
    public void loadLocations(){
        for(int i = 1; i <= 2; i++){
            Location newLocation = new Location();
            newLocation.setRoomNumber(i);
            newLocation.setBuildingName("Building" + i);
            locationDAO.saveOrUpdate(newLocation);
        }
    }

    @Transactional
    //Load 1 department
    public void loadDepartments(){
        Department newDepartment = new Department();
        newDepartment.setDepartmentName("Department1");
        departmentDAO.saveOrUpdate(newDepartment);
    }

    @Transactional
    //Load 1 candidate
    public void loadCandidates(){
        User newCandidate = new User();
        newCandidate.setEmail("CandidateEmail1");
        newCandidate.setPhone("CandidatePhone1");
        newCandidate.setName("CandidateName1");
        newCandidate.setRole(Role.CANDIDATE);
        newCandidate.setPassword("CandidatePassword1");
        userDAO.saveOrUpdate(newCandidate);

    }

    @Transactional
    //Load two participants
    public void loadParticipants(){
        List<Department> departmentList = departmentDAO.getAll();
        for(int i = 1; i <= 2; i++){
            User newParticipant = new User();
            newParticipant.setEmail("ParticipantEmail" + i);
            newParticipant.setPhone("ParticipantPhone" + i);
            newParticipant.setName("ParticipantName" + i);
            newParticipant.setRole(Role.PARTICIPANT);
            newParticipant.setPassword("ParticipantPassword" + i);
            newParticipant.setDepartment(departmentList.get(0));
            userDAO.saveOrUpdate(newParticipant);
        }
    }

    @Transactional
    //load one position
    public void loadPositions(){
        List<Department> departmentList = departmentDAO.getAll();
        Position newPosition = new Position();
        newPosition.setPositionName("PositionName1");
        newPosition.setDepartment(departmentList.get(0));
        positionDAO.saveOrUpdate(newPosition);
    }

    @Transactional
    //load one candidacy
    public void loadCandidacies(){
        List<Position> positionList = positionDAO.getAll();
        List<User> candidateList = userDAO.getAllCandidates();
        Candidacy candidacy = new Candidacy();
        candidacy.setPosition(positionList.get(0));
        candidacy.setSchedule(new Schedule());
        candidacy.setCandidate(candidateList.get(0));
        candidacyDAO.saveOrUpdate(candidacy);
    }

    //load one meeting with two participations
    public void loadMeetings(){
        List<Schedule> scheduleList = scheduleDAO.getAll();
        List<Location> locationList = locationDAO.getAll();
        List<User> participantList = userDAO.getAllParticipants();

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        Meeting newMeeting = new Meeting();
        try {
            newMeeting.setSchedule(scheduleList.get(0));
            newMeeting.setStartTime(sdf.parse("2020-01-01 12:00:00"));
            newMeeting.setEndTime(sdf.parse("2020-01-01 14:00:00"));
            newMeeting.setLocation(locationList.get(0));
            newMeeting.setMeetingType(MeetingType.MEET_FACULTY);

            Participation participation1 = new Participation();
            em.persist(participation1);
            participation1.setCanViewFeedback(false);
            participation1.setCanLeaveFeedback(false);
            participation1.setAlertType("email");
            participation1.setAlert(false);
            participation1.setParticipant(participantList.get(0));
            participation1.setTransientId(1);

            Participation participation2 = new Participation();
            em.persist(participation2);
            participation2.setCanViewFeedback(false);
            participation2.setCanLeaveFeedback(false);
            participation2.setAlertType("email");
            participation2.setAlert(false);
            participation2.setParticipant(participantList.get(1));
            participation2.setTransientId(2);

            newMeeting.addParticipation(participation1);
            newMeeting.addParticipation(participation2);
            meetingDAO.saveOrUpdate(newMeeting);
        }
        catch(Exception e){
            e.printStackTrace();
        }

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
        location.setRoomNumber(113);
        location.setBuildingName("Hibbard");
        location = locationDAO.saveOrUpdate(location);
        location = new Location();
        location.setRoomNumber(114);
        location.setBuildingName("Hibbard");
        location = locationDAO.saveOrUpdate(location);
        location = new Location();
        location.setRoomNumber(115);
        location.setBuildingName("Hibbard");
        location = locationDAO.saveOrUpdate(location);location = new Location();
        location.setRoomNumber(116);
        location.setBuildingName("Hibbard");
        location = locationDAO.saveOrUpdate(location);



        //create participant
        User participant = new User();
        em.persist(participant);
        participant.setUsername("testUsername");
        participant.setPassword("testPassword");
        participant.setRole(Role.DEPARTMENT_ADMIN);
        participant.setName("John Doe");
        participant.setEmail("testEmail");
        participant.setPhone("testPhone");
        participant.setDepartment(department);
        participant = userDAO.saveOrUpdate(participant);

        User participant2 = new User();
        em.persist(participant2);
        participant2.setUsername("testUsername2");
        participant2.setPassword("testPassword2");
        participant2.setRole(Role.PARTICIPANT);
        participant2.setName("Jen Doe");
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

