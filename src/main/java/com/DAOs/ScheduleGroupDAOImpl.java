package com.DAOs;

import com.Entities.Participation;
import com.Entities.Schedule;
import com.Entities.ScheduleGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ScheduleGroupDAOImpl implements ScheduleGroupDAO{
    @PersistenceContext
    EntityManager em;

    public List<ScheduleGroup> getAll(){
        List<ScheduleGroup> scheduleGroupList = em.createQuery(
                "from ScheduleGroup sg LEFT JOIN FETCH sg.position LEFT JOIN FETCH sg.schedules s "
                 + "LEFT JOIN FETCH s.candidate"
                , ScheduleGroup.class)
                .getResultList();
        return scheduleGroupList;
    }

    public ScheduleGroup getById(Integer id){
        ScheduleGroup scheduleGroup = em.createQuery(
                "SELECT sg FROM ScheduleGroup sg WHERE sg.id = :id", ScheduleGroup.class)
                .setParameter("id", id)
                .getSingleResult();
        return scheduleGroup;
    }
    public ScheduleGroup saveOrUpdate(ScheduleGroup scheduleGroup){
        ScheduleGroup savedScheduleGroup = em.merge(scheduleGroup);
        return savedScheduleGroup;
    }

    public void remove(ScheduleGroup scheduleGroup){
        em.remove(scheduleGroup);
    }
}
