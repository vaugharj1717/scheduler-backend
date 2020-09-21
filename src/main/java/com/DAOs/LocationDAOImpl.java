package com.DAOs;

import com.Entities.Location;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LocationDAOImpl implements LocationDAO{
    @PersistenceContext
    EntityManager em;

    public List<Location> getAll(){
        List<Location> locationList = em.createQuery("from Location", Location.class)
                .getResultList();
        return locationList;
    }

    public Location getById(Integer id){
        Location location = em.createQuery(
                "SELECT l FROM Location l WHERE l.id = :id", Location.class)
                .setParameter("id", id)
                .getSingleResult();
        return location;
    }
    public Location saveOrUpdate(Location location){
        Location savedLocation = em.merge(location);
        return savedLocation;
    }

    public void remove(Location location){
        em.remove(location);
    }
}
