package com.DAOs;

import com.Entities.User;
import com.Entities.enumeration.Role;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDAOImpl extends AbstractDAO implements UserDAO {

    @Override
    public User getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        return em.find(User.class, id);
    }

    @Override
    public User saveOrUpdate(User user) {
        //begin transaction
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User savedUser = em.merge(user);
        em.getTransaction().commit();
        return savedUser;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(User.class, id));
        em.getTransaction().commit();
    }

    @Override
    public List<User> getAllParticipants() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select i from User i where i.role = ?1 or i.role = ?2");
        q.setParameter(1, Role.CANDIDATE);
        q.setParameter(2, Role.DEPRATEMENT_ADMIN);
        List<User> userList = q.getResultList();
        return userList;
    }
}
