package com.DAOs;

import com.Entities.Department;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class DepartmentDAOImpl extends AbstractDAO implements DepartmentDAO{

    public List<Department> getAllDepartments(){
        EntityManager em = emf.createEntityManager();
        List<Department> departmentList = em.createQuery("from Department", Department.class).getResultList();
        return departmentList;
    }

    public void saveDepartment(Department department){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
    }
}
