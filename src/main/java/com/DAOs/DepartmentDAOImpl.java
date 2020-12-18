package com.DAOs;

import com.Entities.Department;
import com.Entities.Position;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
public class DepartmentDAOImpl implements DepartmentDAO{

    @PersistenceContext
    EntityManager em;

    public List<Department> getAll(){
        //Selecting all Departments, also populating the department's "position" field in-memory
        List<Department> departmentList = em.createQuery("SELECT d from Department d", Department.class)
                .getResultList();
        return departmentList;
    }

    public Department getById(Integer id){
        Department department = em.createQuery(
                "SELECT d FROM Department d WHERE d.id = :id", Department.class)
                .setParameter("id", id)
                .getSingleResult();
        return department;
    }
    public Department saveOrUpdate(Department department){
            Department savedDepartment = em.merge(department);
            return savedDepartment;
    }

    public void remove(Integer id){
        em.remove(
                em.createQuery("SELECT d FROM Department d WHERE d.id = :id")
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    public List<Position> getPositionsByDepartment(Integer id) {
        Query q = em.createQuery("select i from Position i where i.department.id=?1");
        q.setParameter(1, id);
        List<Position> positionList = q.getResultList();
        return positionList;

    }

    @Override
    public void removeDepartment(Integer id) {
        em.createQuery("UPDATE User u SET u.department = null WHERE u.department.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        em.createQuery("UPDATE Position p SET p.department = null WHERE p.department.id = :id")
                        .setParameter("id", id)
                        .executeUpdate();

        em.remove(
                em.createQuery("SELECT d FROM Department d WHERE d.id = :id")
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }
}
