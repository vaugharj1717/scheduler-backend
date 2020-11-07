package com.DAOs;

import com.App;
import com.Entities.User;
import com.Entities.Department;
import com.Entities.User;
import com.Entities.User;
import com.Entities.enumeration.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        List<User> users = (List<User>) userDAO.getAll();
        assert users.size() == 3;
    }

    @Test
    @Transactional
    public void testGetById() throws Exception {
        List<User> users = (List<User>) userDAO.getAll();
        User user = userDAO.getById(users.get(0).getId());
        assert user.equals(users.get(0));
    }

    @Test
    @Transactional
    public void testGetAllParticipants() throws Exception {
        List<User> users = (List<User>) userDAO.getAllParticipants();
        assert users.size() == 2;
        for(User user : users){
            assert user.getRole().equals(Role.PARTICIPANT);
        }
    }

    @Test
    @Transactional
    public void testSaveOrUpdate() throws Exception {
        User user = new User();
        user = userDAO.saveOrUpdate(user);

        em.flush();
        em.detach(user);

        User resultUser = userDAO.getById(user.getId());
        assert resultUser.equals(user);
    }

    @Test
    @Transactional
    public void testFindByEmail() throws Exception {
        User user = userDAO.findByEmail("ParticipantEmail1");
        assert user.getEmail().equals("ParticipantEmail1");
    }

    @Transactional
    public void testRemove() throws Exception {
        List<User> users = (List<User>) userDAO.getAll();
        userDAO.remove(users.get(0).getId());

        em.flush();
        for(User user : users) em.detach(user);

        User user = userDAO.getById(users.get(0).getId());
    }

    @Test
    @Transactional
    public void testChangeRole() throws Exception {
        //get candidate and change role
        User user = userDAO.getAllCandidates().get(0);
        System.out.println(user.getRole() + "!!");
        userDAO.changeRole(Role.PARTICIPANT, user.getId());

        //flush database changes and evict user from context
        em.flush();
        em.detach(user);

        User resultUser = userDAO.getById(user.getId());

        assert resultUser.getRole().equals(Role.PARTICIPANT);
    }

    @Test
    @Transactional
    public void testGetUserWithDepart() {
        List<User> users = (List<User>) userDAO.getAll();
        Boolean hasDepart = false;
        for(User user : users){
            User user1 = userDAO.getUserWithDepart(user.getId());
            if (user1.getDepartment() != null) {
                if (user1.getDepartment().getDepartmentName() != null) {
                    hasDepart = true;
                }
            }
        }

        assert hasDepart.equals(true);
    }
}
