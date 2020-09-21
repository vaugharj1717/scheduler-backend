package com.DAOs;

import com.Entities.Participation;
import com.Entities.ScheduleGroup;
import com.Entities.User;
import com.Entities.enumeration.Role;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    EntityManager em;

    public List<User> getAll(){
        List<User> userList = em.createQuery("from User", User.class)
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

    @Override
    public List<User> getAllParticipants() {
        Query q = em.createQuery("select i from User i where i.role = ?1 or i.role = ?2");
        q.setParameter(1, Role.CANDIDATE);
        q.setParameter(2, Role.DEPRATEMENT_ADMIN);
        List<User> userList = q.getResultList();
        return userList;
    }

}
