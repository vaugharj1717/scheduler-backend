package com.DAOs;

import com.Entities.Position;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PositionDaoImpl extends AbstractDAO implements PositionDao {
    @Override
    public List<Position> getPositionsByDepartement(Integer id) {
        EntityManager em = emf.createEntityManager();
       Query q = em.createQuery("select i from Position i where i.department.id=?1");
       q.setParameter(1, id);
       List<Position> positionList = q.getResultList();
       return positionList;

    }
}
