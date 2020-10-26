package com.controllers;


import com.App;
import com.Controllers.PositionController;
import com.Entities.Position;
import com.Entities.Position;
import com.Services.PositionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONObject;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class PositionControllerTest {

    @Mock
    private PositionService positionService;

    @InjectMocks
    private PositionController positionController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(positionController).build();
    }

    @Test
    public void testGetPositionsByPosition() throws Exception{
        //define return value of dependency
        List<Position> positions = new ArrayList<>();
        Position position1 = new Position();
        Position position2 = new Position();
        position1.setPositionName("position1");
        position2.setPositionName("position2");
        positions.add(position1);
        positions.add(position2);
        when(positionService.getPositionsByDepartment(1)).thenReturn(positions);

        //define expected value
        ObjectWriter ow = new ObjectMapper().writer();
        String expectedJson = ow.writeValueAsString(positions);


        //perform test
        String response = mockMvc.perform(get("/position/department/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assert response.equals(expectedJson);
    }



}
