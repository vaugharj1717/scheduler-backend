package com.services;

import com.App;
import com.DAOs.ScheduleDAO;
import com.Entities.Schedule;
import com.Services.ScheduleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class ScheduleServiceImplTest {


    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Mock
    private ScheduleDAO scheduleDAO;




    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSchedule() throws Exception {
        //define return value of dependency
        Schedule schedule = new Schedule();
        schedule.setId(1);
        when(scheduleDAO.getById(1)).thenReturn(schedule);

        //perform test
        Schedule actual = scheduleService.getSchedule(1);
        assert actual.equals(schedule);
    }
}
