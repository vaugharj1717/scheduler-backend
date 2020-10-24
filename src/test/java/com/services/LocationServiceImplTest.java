package com.services;

import com.App;
import com.DAOs.LocationDAO;
import com.Entities.Location;
import com.Services.LocationServiceImpl;
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
public class LocationServiceImplTest {

    @InjectMocks
    private LocationServiceImpl locationService;

    @Mock
    private LocationDAO locationDao;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() throws Exception {
        //define return value of dependency
        List<Location> locations= new ArrayList<Location>();
        Location location1 = new Location();
        location1.setBuildingName("building1");
        Location location2 = new Location();
        location2.setBuildingName("building2");
        locations.add(location1);
        locations.add(location2);
        when(locationDao.getAll()).thenReturn(locations);

        //perform test
        List<Location> ret = locationService.getAllLocations();
        assert ret.size() == 2;
        assert ret.get(0).getBuildingName().equals("building1");
        assert ret.get(1).getBuildingName().equals("building2");
    }
}
