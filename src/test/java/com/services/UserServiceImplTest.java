package com.services;

import com.App;
import com.Configuration.FileStorageProperties;
import com.DAOs.CandidacyDAO;
import com.DAOs.PositionDAO;
import com.DAOs.ScheduleDAO;
import com.DAOs.UserDAO;
import com.Entities.Schedule;
import com.Entities.User;
import com.Entities.enumeration.Role;
import com.Services.ScheduleServiceImpl;
import com.Services.UserServiceImpl;
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
}
