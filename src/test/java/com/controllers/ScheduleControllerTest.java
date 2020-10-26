package com.controllers;


import com.App;
import com.Controllers.ScheduleController;
import com.Entities.Schedule;
import com.Entities.Schedule;
import com.Services.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class ScheduleControllerTest {

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private ScheduleController scheduleController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(scheduleController).build();
    }

    @Test
    public void testGetSchedule() throws Exception{
        //define return value of dependency
        Schedule schedule = new Schedule();
        schedule.setId(1);
        when(scheduleService.getSchedule(1)).thenReturn(schedule);

        //define expected value
        ObjectWriter ow = new ObjectMapper().writer();
        String expectedJson = ow.writeValueAsString(schedule);


        //perform test
        String response = mockMvc.perform(get("/schedule/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assert response.equals(expectedJson);
    }



}
