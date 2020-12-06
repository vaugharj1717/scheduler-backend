package com.controllers;

import com.App;
import com.Controllers.DepartmentController;
import com.Controllers.UserController;
import com.Entities.Candidacy;
import com.Entities.Department;
import com.Entities.User;
import com.Entities.enumeration.Role;
import com.Services.DepartmentService;
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

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateCandidate() throws Exception{
        //mock dependency behavior
        User candidate = new User();
        candidate.setId(1); candidate.setEmail("testEmail"); candidate.setPhone("testPhone");
        candidate.setRole(Role.CANDIDATE); candidate.setName("testName");
        Candidacy candidacy = new Candidacy();
        candidacy.setCandidate(candidate);
        when(userService.createCandidate(1, "testName", "testEmail")).thenReturn(candidacy);

        //establish expected result
        ObjectWriter ow = new ObjectMapper().writer();
        String expected = ow.writeValueAsString(candidacy);

        //perform test
        JSONObject request = new JSONObject();
        request.put("name", "testName");
        request.put("email", "testEmail");
        String requestJson = request.toString();

        String response = mockMvc
                .perform(post("/user/candidate/1")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assert response.equals(expected);
    }

    @Test
    public void testGetUserWithDepart() throws Exception{
        //mock dependency behavior
        User user = new User();
        user.setId(1000); user.setEmail("testEmail"); user.setPhone("testPhone");
        user.setRole(Role.DEPARTMENT_ADMIN); user.setName("testName");
        Department department = new Department();
        department.setDepartmentName("testName");
        user.setDepartment(department);
        when(userService.getUserWithDepart(1000)).thenReturn(user);

        //establish expected result
        ObjectWriter ow = new ObjectMapper().writer();
        String expected = ow.writeValueAsString(user);

        String response = mockMvc
                .perform(post("/user/getUserWithDepart/1000")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assert response.equals(expected);
    }

    @Test
    public void testUpdateInfo() throws Exception{
        //mock dependency behavior
        User user = new User();
        user.setId(1); user.setAddress("testAddress"); user.setPhone("testPhone");
        user.setBio("testBio"); user.setUniversity("testUniversity");

        when(userService.updateInfo(1, "testName", "testEmail", "testAddress", "testPhone", "testBio", "testUniversity", null)).thenReturn(user);

        //establish expected result
        ObjectWriter ow = new ObjectMapper().writer();
        String expected = ow.writeValueAsString(user);

        //perform test
        JSONObject request = new JSONObject();
        request.put("address", "testAddress");
        request.put("phone", "testPhone");
        request.put("bio", "testBio");
        request.put("university", "testUniversity");
        String requestJson = request.toString();

        String response = mockMvc
                .perform(patch("/user/1/updateInfo")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assert response.equals(expected);
    }
}
