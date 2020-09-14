package com.DAOs;

import com.Entities.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
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

}
