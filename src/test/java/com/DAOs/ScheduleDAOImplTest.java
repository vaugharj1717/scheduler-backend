package com.DAOs;

import com.App;
import com.Entities.Schedule;
import com.Entities.Schedule;
import com.Entities.Schedule;
import com.Entities.User;
import com.Entities.enumeration.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class ScheduleDAOImplTest {

    @Autowired
    private ScheduleDAO scheduleDAO;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        List<Schedule> schedules = (List<Schedule>) scheduleDAO.getAll();
        assert schedules.size() == 1;
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Transactional
    public void testRemove() throws Exception {
        List<Schedule> schedules = (List<Schedule>) scheduleDAO.getAll();
        scheduleDAO.remove(schedules.get(0).getId());

        em.flush();
        for(Schedule schedule : schedules) em.detach(schedule);

        Schedule schedule = scheduleDAO.getById(schedules.get(0).getId());
    }

    @Test
    @Transactional
    public void testSaveOrUpdate() throws Exception {
        Schedule schedule = new Schedule();
        schedule = scheduleDAO.saveOrUpdate(schedule);

        em.flush();
        em.detach(schedule);

        Schedule resultSchedule = scheduleDAO.getById(schedule.getId());
        assert resultSchedule.equals(schedule);
    }

    @Test
    @Transactional
    public void testGetById() throws Exception {
        List<Schedule> schedules = (List<Schedule>) scheduleDAO.getAll();
        Schedule schedule = scheduleDAO.getById(schedules.get(0).getId());
        assert schedule.equals(schedules.get(0));
    }


}
