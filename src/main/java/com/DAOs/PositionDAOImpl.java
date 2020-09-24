package com.DAOs;

import com.Entities.Department;
import com.Entities.Participation;
import com.Entities.Position;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PositionDAOImpl implements PositionDAO{
    @PersistenceContext
    EntityManager em;

    public List<Position> getAll(){
        List<Position> positionList = em.createQuery("SELECT p from Position p LEFT JOIN FETCH p.department LEFT JOIN FETCH p.candidacies c LEFT JOIN FETCH c.candidate", Position.class)
                .getResultList();
        return positionList;
    }

    public Position getById(Integer id){
        Position position = em.createQuery(
                "SELECT p FROM Position p WHERE p.id = :id", Position.class)
                .setParameter("id", id)
                .getSingleResult();
        return position;
    }
    public Position saveOrUpdate(Position position){
        Position savedPosition = em.merge(position);
        return savedPosition;
    }

    @Override
    public List<Position> getPositionsByDepartment(Integer id) {
        Query q = em.createQuery("select i from Position i where i.department.id=?1");
        q.setParameter(1, id);
        List<Position> positionList = q.getResultList();
        return positionList;

    }

    public void remove(Integer id){
        em.remove(
                em.createQuery("SELECT p FROM Position p WHERE p.id = :id")
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }
}
