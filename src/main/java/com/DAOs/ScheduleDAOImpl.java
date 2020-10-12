package com.DAOs;

import com.Entities.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ScheduleDAOImpl implements ScheduleDAO{

    @PersistenceContext
    EntityManager em;

    public List<Schedule> getAll(){
        List<Schedule> scheduleList = em.createQuery("SELECT s from Schedule s", Schedule.class)
                .getResultList();
        return scheduleList;
    }

    public Schedule getById(Integer id){
        Schedule schedule = em.createQuery(
                "SELECT s FROM Schedule s LEFT JOIN FETCH s.meetings m LEFT JOIN FETCH m.location l " +
                        "LEFT JOIN FETCH m.participations p LEFT JOIN FETCH p.participant " +
                        "LEFT JOIN FETCH s.candidacy c LEFT JOIN FETCH c.candidate WHERE s.id = :id"
                , Schedule.class)
                .setParameter("id", id)
                .getSingleResult();
        return schedule;
    }
    public Schedule saveOrUpdate(Schedule schedule){
        Schedule savedSchedule = em.merge(schedule);
        return savedSchedule;
    }

    public void remove(Integer id){
        //update candidacy foreign-key to avoid referential constraint violation
        em.createQuery("UPDATE Candidacy c SET c.schedule = null WHERE c.schedule.id = :id ")
                .setParameter("id", id)
                .executeUpdate();
        //remove schedule by Id
        em.remove(
                em.createQuery("SELECT s FROM Schedule s WHERE s.id = :id")
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    @Override
    public void deleteAllMeetingByScheduleId(Integer scheduleId) {
        /*em.createQuery("UPDATE Schedule s SET s.meetings = null WHERE s.id = :id")
                .setParameter("id", scheduleId)
                .executeUpdate();
        em.createQuery("UPDATE Participation p SET p.meeting = null WHERE p.meeting.schedule.id = :id")
                .setParameter("id", scheduleId)
                .executeUpdate();*/
        for(Meeting m : em.createQuery("SELECT m FROM Meeting m WHERE m.schedule.id = :id", Meeting.class)
                .setParameter("id", scheduleId).getResultList()){
                    em.remove(m);
        }

    }

}
