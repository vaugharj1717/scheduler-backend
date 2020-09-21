package com.DAOs;

import com.Entities.Participation;
import com.Entities.Position;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ParticipationDAOImpl implements ParticipationDAO{
    @PersistenceContext
    EntityManager em;

    public List<Participation> getAll(){
        List<Participation> participationList = em.createQuery("SELECT p from Participation p", Participation.class)
                .getResultList();
        return participationList;
    }

    public Participation getById(Integer id){
        Participation participation = em.createQuery(
                "SELECT p FROM Participation p WHERE p.id = :id", Participation.class)
                .setParameter("id", id)
                .getSingleResult();
        return participation;
    }

    public Participation saveOrUpdate(Participation participation){
        Participation savedParticipation = em.merge(participation);
        return savedParticipation;
    }

    public void remove(Participation participation){
        em.remove(participation);
    }
}
