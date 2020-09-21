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
        List<Schedule> scheduleList = em.createQuery("from Schedule", Schedule.class)
                .getResultList();
        return scheduleList;
    }

    public Schedule getById(Integer id){
        Schedule schedule = em.createQuery(
                "SELECT s FROM Schedule s WHERE s.id = :id", Schedule.class)
                .setParameter("id", id)
                .getSingleResult();
        return schedule;
    }
    public Schedule saveOrUpdate(Schedule schedule){
        Schedule savedSchedule = em.merge(schedule);
        return savedSchedule;
    }

    public void remove(Schedule schedule){
        em.remove(schedule);
    }
}
