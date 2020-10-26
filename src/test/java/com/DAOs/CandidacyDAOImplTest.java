package com.DAOs;

import com.App;
import com.Entities.*;
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
public class CandidacyDAOImplTest {

    @Autowired
    private CandidacyDAO candidacyDAO;

    @Autowired
    private DepartmentDAO departmentDAO;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        List<Candidacy> candidacies = (List<Candidacy>) candidacyDAO.getAll();
        assert candidacies.size() == 1;
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Transactional
    public void testRemove() throws Exception {
        List<Candidacy> candidacies = (List<Candidacy>) candidacyDAO.getAll();
        candidacyDAO.remove(candidacies.get(0).getId());

        em.flush();
        for(Candidacy candidacy : candidacies) em.detach(candidacy);

        Candidacy candidacy = candidacyDAO.getById(candidacies.get(0).getId());
    }

    @Test
    @Transactional
    public void testSaveOrUpdate() throws Exception {
        Candidacy candidacy = new Candidacy();
        candidacy = candidacyDAO.saveOrUpdate(candidacy);

        em.flush();
        em.detach(candidacy);

        Candidacy resultCandidacy = candidacyDAO.getById(candidacy.getId());
        assert resultCandidacy.equals(candidacy);
    }


    @Test
    @Transactional
    public void testGetById() throws Exception {
        List<Candidacy> candidacies = (List<Candidacy>) candidacyDAO.getAll();
        Candidacy candidacy = candidacyDAO.getById(candidacies.get(0).getId());
        assert candidacy.equals(candidacies.get(0));
    }

    @Test
    @Transactional
    public void testPositionsByDepartment() throws Exception {
        Department department = departmentDAO.getAll().get(0);
        List<Position> positionList = departmentDAO.getPositionsByDepartment(department.getId());
        assert positionList.size() == 1;

    }


}
