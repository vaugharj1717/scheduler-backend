package com.DAOs;

import com.App;
import com.Entities.Position;
import com.Entities.Position;
import com.Entities.Position;
import com.Entities.Schedule;
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
public class PositionDAOImplTest {

    @Autowired
    private PositionDAO positionDAO;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        List<Position> positions = (List<Position>) positionDAO.getAll();
        assert positions.size() == 1;
    }

    @Test
    @Transactional
    public void testSaveOrUpdate() throws Exception {
        Position position = new Position();
        position = positionDAO.saveOrUpdate(position);

        em.flush();
        em.detach(position);

        Position resultPosition = positionDAO.getById(position.getId());
        assert resultPosition.equals(position);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Transactional
    public void testRemove() throws Exception {
        List<Position> positions = (List<Position>) positionDAO.getAll();
        positionDAO.remove(positions.get(0).getId());

        em.flush();
        for(Position position : positions) em.detach(position);

        Position Position = positionDAO.getById(positions.get(0).getId());
    }

    @Test
    @Transactional
    public void testGetById() throws Exception {
        List<Position> positions = (List<Position>) positionDAO.getAll();
        Position position = positionDAO.getById(positions.get(0).getId());
        assert position.equals(positions.get(0));
    }


}
