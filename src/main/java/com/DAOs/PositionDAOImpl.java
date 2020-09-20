package com.DAOs;

import com.Entities.Position;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PositionDAOImpl extends AbstractDAO implements PositionDAO{
    public Position getById(Integer positionId){
        System.out.println("In PositionDAO");
        EntityManager em = emf.createEntityManager();
        Position position = em.find(Position.class, positionId);
        return position;
    }
}
