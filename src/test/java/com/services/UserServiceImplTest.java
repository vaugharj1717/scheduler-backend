package com.services;

import com.App;
import com.Configuration.FileStorageProperties;
import com.DAOs.CandidacyDAO;
import com.DAOs.PositionDAO;
import com.DAOs.ScheduleDAO;
import com.DAOs.UserDAO;
import com.Entities.Department;
import com.Entities.Schedule;
import com.Entities.User;
import com.Entities.enumeration.Role;
import com.Services.ScheduleServiceImpl;
import com.Services.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration

public class UserServiceImplTest {

//    @Autowired
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDAO userDAO;

    @Mock
    PositionDAO positionDAO;

    @Mock
    CandidacyDAO candidacyDAO;

    @Spy
    PasswordEncoder encoder;

    @Spy
    FileStorageProperties fsp;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllParticipants() throws Exception {
        //define return value of dependency
        User participant1 = new User();
        User participant2 = new User();
        participant1.setRole(Role.PARTICIPANT);
        participant2.setRole(Role.PARTICIPANT);
        List<User> participantList = new ArrayList<User>();
        participantList.add(participant1);
        participantList.add(participant2);
        when(userDAO.getAllParticipants()).thenReturn(participantList);

        //run test
        List<User> retList = userService.getAllParticipants();
        assert retList.size() == 2;
        for(User participant : participantList) assert participant.getRole().equals(Role.PARTICIPANT);
    }

    @Test
    public void testGetUserWithDepart() {
        User user = new User();
        user.setId(1000); user.setEmail("testEmail"); user.setPhone("testPhone");
        user.setRole(Role.DEPARTMENT_ADMIN); user.setName("testName");
        Department department = new Department();
        department.setDepartmentName("testName");
        user.setDepartment(department);
        when(userDAO.getUserWithDepart(1000)).thenReturn(user);

        //run test
        User testUser = userService.getUserWithDepart(1000);
        assert testUser.getDepartment().getDepartmentName().equals("testName");
    }


    @Test
    public void testUpdateInfo() throws Exception {

        User user = new User();
        user.setId(1); user.setAddress("testAddress"); user.setPhone("testPhone");
        user.setBio("testBio"); user.setUniversity("testUniversity");
        when(userDAO.getById(1)).thenReturn(user);

        User user2 = new User();
        user2.setId(1); user2.setAddress("testAddress2"); user2.setPhone("testPhone2");
        user2.setBio("testBio2"); user2.setUniversity("testUniversity2");
        when(userDAO.saveOrUpdate(user)).thenReturn(user2);

        //run test
        User retUser = userService.updateInfo(1, "testAddress", "testPhone", "testBio", "testUniversity");
        assert retUser.equals(user2);
        assert retUser.getAddress().equals(user2.getAddress());
        assert retUser.getPhone().equals(user2.getPhone());
        assert retUser.getBio().equals(user2.getBio());
        assert retUser.getUniversity().equals(user2.getUniversity());
    }
}
