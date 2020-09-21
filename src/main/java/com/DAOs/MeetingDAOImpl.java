package com.DAOs;

import com.Entities.Meeting;
import com.Entities.Participation;
import com.Entities.Position;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MeetingDAOImpl implements MeetingDAO{
    @PersistenceContext
    EntityManager em;

    public List<Meeting> getAll(){
        List<Meeting> meetingList = em.createQuery("from Meeting", Meeting.class)
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
}
