package com.DAOs;

import com.Entities.Participation;
import com.Entities.Position;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
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

    public void remove(Integer id){
        em.remove(
                em.createQuery("SELECT p FROM Participation p WHERE p.id = :id")
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    public List<Participation> getAllParticipationByMeetingId(Integer meetingId) {
        List<Participation> participationList = em.createQuery("SELECT p FROM Participation p  LEFT JOIN FETCH p.participant LEFT JOIN FETCH p.meeting WHERE (p.meeting.id = :id)", Participation.class)
                .setParameter("id", meetingId)
                .getResultList();
        return participationList;
    }
}
