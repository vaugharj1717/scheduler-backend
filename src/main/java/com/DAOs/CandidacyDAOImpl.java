package com.DAOs;

import com.Entities.Candidacy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CandidacyDAOImpl implements CandidacyDAO {
    @PersistenceContext
    EntityManager em;

    public List<Candidacy> getAll(){
        List<Candidacy> candidacyList = em.createQuery(
                "from Candidacy c LEFT JOIN FETCH c.position LEFT JOIN FETCH c.schedule "
                 + "LEFT JOIN FETCH c.candidate"
                , Candidacy.class)
                .getResultList();
        return candidacyList;
    }

    public Candidacy getById(Integer id){
        Candidacy candidacy = em.createQuery(
                "SELECT c from Candidacy c WHERE c.id = :id", Candidacy.class)
                .setParameter("id", id)
                .getSingleResult();
        return candidacy;
    }
    public Candidacy saveOrUpdate(Candidacy candidacy){
        Candidacy savedCandidacy = em.merge(candidacy);
        return savedCandidacy;
    }

    public void remove(Integer id){
        em.remove(
                em.createQuery("SELECT c FROM Candidacy c WHERE c.id = :id")
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }
}
