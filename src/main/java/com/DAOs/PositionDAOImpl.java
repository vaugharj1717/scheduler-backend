package com.DAOs;

import com.Entities.Department;
import com.Entities.Participation;
import com.Entities.Position;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PositionDAOImpl implements PositionDAO{
    @PersistenceContext
    EntityManager em;

    public List<Position> getAll(){
        List<Position> positionList = em.createQuery("from Position", Position.class)
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

    public void remove(Position position){
        em.remove(position);
    }
}