package com.services;

import com.App;
import com.DAOs.*;
import com.Entities.*;
import com.Entities.enumeration.MeetingType;
import com.Services.MeetingServiceImpl;
import com.Services.PositionServiceImpl;
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
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class PositionServiceImplTest {

    @InjectMocks
    private PositionServiceImpl positionService;

    @Mock
    private PositionDAO positionDAO;




    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPositionsByDepartment() throws Exception {
        //define return value of dependency
        Position position = new Position();
        position.setId(1);
        List<Position> positionList = new ArrayList<Position>();
        positionList.add(position);
        when(positionDAO.getPositionsByDepartment(1)).thenReturn(positionList);

        //perform test
        List<Position> actual = positionService.getPositionsByDepartment(1);
        assert actual.equals(positionList);
    }
}
