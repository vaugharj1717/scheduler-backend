package com.DAOs;

import com.Entities.Participation;
import com.Entities.ScheduleGroup;
import com.Entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    EntityManager em;

    public List<User> getAll(){
        List<User> userList = em.createQuery("SELECT u from User u", User.class)
                .getResultList();
        return userList;
    }

    public List<User> getAllCandidates(){
        List<User> userList = em.createQuery("SELECT u from User u WHERE u.role = \'candidate\'", User.class)
                .getResultList();
        return userList;
    }

    public User getById(Integer id){
        User user = em.createQuery(
                "SELECT u FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
        return user;
    }
    public User saveOrUpdate(User user){
        User savedUser = em.merge(user);
        return savedUser;
    }

    public void remove(User user){
        em.remove(user);
    }

}
