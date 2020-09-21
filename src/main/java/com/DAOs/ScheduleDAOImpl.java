package com.DAOs;

import com.Entities.Department;
import com.Entities.Position;
import com.Entities.ScheduleGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ScheduleDAOImpl extends AbstractDAO implements ScheduleDAO{

    public List<ScheduleGroup> getAllSchedules() {
        EntityManager em = emf.createEntityManager();
        List<ScheduleGroup> scheduleGroupList = em.createQuery(
                "from ScheduleGroup sg LEFT JOIN FETCH sg.schedules LEFT JOIN FETCH sg.position", ScheduleGroup.class
        ).getResultList();
        return scheduleGroupList;
    }

    public ScheduleGroup createScheduleGroup(Position position){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            ScheduleGroup newScheduleGroup = new ScheduleGroup();
            newScheduleGroup.setPosition(position);
            ScheduleGroup mergedScheduleGroup = em.merge(newScheduleGroup);
            em.getTransaction().commit();
            return mergedScheduleGroup;
        }
        catch(Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
            return null;
        }
    }
}
