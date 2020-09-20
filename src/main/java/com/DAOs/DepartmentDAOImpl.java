package com.DAOs;

import com.Entities.Department;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
//DAO layer responsibilities
//  1) Access data from database, and it will automatically be converted into Entities
//  2) For updates/deletes/inserts, manage transactions with
//      em.getTransaction.begin(),   em.getTransaction().commit(),   em.getTransaction().rollback()
//          start transaction              save transaction                 undo transaction (if error)
public class DepartmentDAOImpl extends AbstractDAO implements DepartmentDAO{

    public List<Department> getAllDepartments(){
        //instantiate EntityManager object and use to select all departments from database
        EntityManager em = emf.createEntityManager();
        List<Department> departmentList = em.createQuery("from Department d JOIN FETCH d.positions", Department.class).getResultList();
        return departmentList;
    }

    public void saveDepartment(Department department){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try{
            em.persist(department);
            em.getTransaction().commit();
        }
        catch(Exception e){
            em.getTransaction().rollback();
        }

    }
}
