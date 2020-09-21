package com.DAOs;

import com.Entities.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public Meeting getById(Integer id){
        Meeting meeting = em.createQuery(
                "SELECT m from Meeting m LEFT JOIN FETCH m.location LEFT JOIN FETCH m.participations p " +
                        "LEFT JOIN FETCH p.user WHERE m.id = :id"
                , Meeting.class)
                .setParameter("id", id)
                .getSingleResult();
        return meeting;
    }
    public Meeting saveOrUpdate(Meeting meeting){
        Meeting savedMeeting = em.merge(meeting);
        return savedMeeting;
    }

    public void remove(Meeting meeting){
        em.remove(meeting);
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
