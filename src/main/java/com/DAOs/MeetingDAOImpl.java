package com.DAOs;

import com.Entities.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class MeetingDAOImpl implements MeetingDAO{
    @PersistenceContext
    EntityManager em;

    public List<Meeting> getAll(){
        List<Meeting> meetingList = em.createQuery("SELECT m from Meeting m", Meeting.class)
                .getResultList();
        return meetingList;
    }

    public List<Meeting> getUpcomingMeetings(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowString = sdf.format(now);
        List<Meeting> upcomingMeetingList = em.createQuery(
                "SELECT DISTINCT m from Meeting m LEFT JOIN FETCH m.location l LEFT JOIN FETCH m.participations p LEFT JOIN FETCH p.participant " +
                        "LEFT JOIN FETCH m.schedule s LEFT JOIN FETCH s.candidacy c LEFT JOIN FETCH c.candidate " +
                "WHERE (m.startTime >= '" + nowString + "')", Meeting.class).getResultList();
        return upcomingMeetingList;
    }

    public List<Meeting> getPastMeetings(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowString = sdf.format(now);
        List<Meeting> pastMeetingList = em.createQuery(
                "SELECT DISTINCT m from Meeting m LEFT JOIN FETCH m.location l LEFT JOIN FETCH m.participations p LEFT JOIN FETCH p.participant " +
                        "LEFT JOIN FETCH m.schedule s LEFT JOIN FETCH s.candidacy c LEFT JOIN FETCH c.candidate " +
                        "WHERE (m.endTime <= '" + nowString + "')", Meeting.class).getResultList();
        return pastMeetingList;
    }

    public Meeting getById(Integer id){
        Meeting meeting = em.createQuery(
                "SELECT m from Meeting m LEFT JOIN FETCH m.location LEFT JOIN FETCH m.participations p " +
                        "LEFT JOIN FETCH p.participant WHERE m.id = :id"
                , Meeting.class)
                .setParameter("id", id)
                .getSingleResult();
        return meeting;
    }

    public List<Meeting> getConflictingUserSchedules(Integer candidateId, List<Integer> participantList, Date startTime, Date endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeString = sdf.format(startTime);
        String endTimeString = sdf.format(endTime);
        System.out.println(startTimeString);
        System.out.println(endTimeString);
        List<Meeting> conflictingMeetingList = em.createQuery(
                "SELECT DISTINCT m from Meeting m JOIN m.participations p JOIN p.participant pt JOIN m.schedule s JOIN s.candidacy c " +
                        "JOIN c.candidate u " +
                        "WHERE (pt.id IN :participantList OR u.id = :candidateId) " +
                        //meetings that begin somewhere between start and end of new meeting
                        "AND ((m.startTime >= '" + startTimeString + "' AND m.startTime <= '" + endTimeString + "') " +
                        //and meetings that end somewhere between start and end of new meeting
                        "OR (m.endTime >= '" + startTimeString + "' AND m.endTime <= '" + endTimeString + "') " +
                        //and meetings that begin before the start of new meeting and end after start of new meeting
                        "OR (m.startTime <= '" + startTimeString + "' AND m.endTime >= '" + endTimeString + "'))"
                , Meeting.class)
                .setParameter("participantList", participantList)
                .setParameter("candidateId", candidateId)
                //.setParameter("startTime", startTime)
                //.setParameter("endTime", endTime)
                .getResultList();
        return conflictingMeetingList;


    }
    public List<Meeting> getConflictingLocations(Integer locationId, Date startTime, Date endTime){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeString = sdf.format(startTime);
        String endTimeString = sdf.format(endTime);
//        System.out.println(startTimeString);
//        System.out.println(endTimeString);
//        System.out.println(locationId);

        //get all meetings for all users overlapping in time and in same location as new meeting
        List<Meeting> conflictingMeetingList = em.createQuery(
                "SELECT DISTINCT m from Meeting m JOIN m.location l " +
                        "WHERE (l.id = :locationId) " +
                        //meetings that begin somewhere between start and end of new meeting
                        "AND ((m.startTime >= '" + startTimeString + "' AND m.startTime <= '" + endTimeString + "') " +
                        //and meetings that end somewhere between start and end of new meeting
                        "OR (m.endTime >= '" + startTimeString + "' AND m.endTime <= '" + endTimeString + "') " +
                        //and meetings that begin before the start of new meeting and end after start of new meeting
                        "OR (m.startTime <= '" + startTimeString + "' AND m.endTime >= '" + endTimeString + "'))"
                , Meeting.class)
                .setParameter("locationId", locationId)
                //.setParameter("startTime", startTime)
                //.setParameter("endTime", endTime)
                .getResultList();

        return conflictingMeetingList;
    }

    public Meeting saveOrUpdate(Meeting meeting){
        Meeting savedMeeting = em.merge(meeting);
        return savedMeeting;
    }

    public void remove(Meeting meeting){
        em.remove(meeting);
    }

    public void remove(Integer id){
        /*em.createQuery("UPDATE Schedule s SET s.meeting = null WHERE s.meeting.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        em.createQuery("UPDATE Participation p SET p.meeting = null WHERE p.meeting.id = :id")
                .setParameter("id", id)
                .executeUpdate();*/
        em.remove(
                em.createQuery("SELECT m FROM Meeting m WHERE m.id = :id")
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    public List<Meeting> getByLocation(Location location){
        List<Meeting> meetingList = em.createQuery("from Meeting m WHERE m.location = :location"
                , Meeting.class)
                .setParameter("location", location)
                .getResultList();
        return meetingList;
    }

    public List<Meeting> getByUserList(List<User> users){
        StringBuilder paramString = new StringBuilder();
        if(users != null){
            for(int i = 0; i < users.size(); i++){
                paramString.append(":param" + i);
                if(i != users.size() - 1) paramString.append(",");
            }
            Query query = em.createQuery("SELECT m from Meeting m JOIN m.participations p JOIN p.user u " +
                    "WHERE u.id IN (" + paramString.toString() + ")", Meeting.class);
            for(int i = 0; i < users.size(); i++){
                query.setParameter("param" + i, users.get(i).getId());
            }
            List<Meeting> meetingList = query.getResultList();
            return meetingList;
        }
        else return null;
    }


}
