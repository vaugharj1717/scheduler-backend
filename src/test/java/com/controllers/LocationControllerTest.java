package com.controllers;

import com.App;
import com.Controllers.LocationController;
import com.Controllers.UserController;
import com.Entities.Candidacy;
import com.Entities.Location;
import com.Entities.User;
import com.Entities.enumeration.Role;
import com.Services.LocationService;
import com.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
    }

    @Test
    public void testGetAllLocations() throws Exception{
        //mock dependency behavior
        Location location1 = new Location();
        location1.setBuildingName("TestBuildingName");
        location1.setRoomNumber(1);
        List<Location> locationList = new ArrayList<Location>();
        locationList.add(location1);

        when(locationService.getAllLocations()).thenReturn(locationList);

        //establish expected result
        ObjectWriter ow = new ObjectMapper().writer();
        String expected = ow.writeValueAsString(locationList);

        //perform test
        String response = mockMvc
                .perform(get("/location"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assert response.equals(expected);
    }
}
