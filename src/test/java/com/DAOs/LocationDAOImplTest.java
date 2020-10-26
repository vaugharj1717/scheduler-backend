package com.DAOs;

import com.App;
import com.Entities.Location;
import com.Entities.Location;
import com.Entities.Location;
import com.Entities.Meeting;
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
public class LocationDAOImplTest {

    @Autowired
    private LocationDAO locationDAO;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        List<Location> locations = locationDAO.getAll();
        assert locations.size() == 2;
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Transactional
    public void testRemove() throws Exception {
        List<Location> locations = (List<Location>) locationDAO.getAll();
        locationDAO.remove(locations.get(0).getId());

        em.flush();
        for(Location Location : locations) em.detach(Location);

        Location location = locationDAO.getById(locations.get(0).getId());
    }

    @Test
    @Transactional
    public void testSaveOrUpdate() throws Exception {
        Location location = new Location();
        location = locationDAO.saveOrUpdate(location);

        em.flush();
        em.detach(location);

        Location resultLocation = locationDAO.getById(location.getId());
        assert resultLocation.equals(location);
    }

    @Test
    @Transactional
    public void testGetById() throws Exception {
        List<Location> locations = (List<Location>) locationDAO.getAll();
        Location location = locationDAO.getById(locations.get(0).getId());
        assert location.equals(locations.get(0));
    }


}
