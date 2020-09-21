package com.DAOs;

import com.Entities.Department;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


//DAO layer responsibilities
//  1) Access data from database, and it will automatically be converted into Entities
//  2) For updates/deletes/inserts, manage transactions with
//      em.getTransaction.begin(),   em.getTransaction().commit(),   em.getTransaction().rollback()
//          start transaction              save transaction                 undo transaction (if error)
@Repository
public class DepartmentDAOImpl extends AbstractDAO implements DepartmentDAO{

    public List<Department> getAllDepartments(){
        //instantiate EntityManager object and use to select all departments from database
        EntityManager em = emf.createEntityManager();
        //Selecting all Departments, also populating the department's "position" field in-memory
        List<Department> departmentList = em.createQuery("from Department d LEFT JOIN FETCH d.positions", Department.class)
                .getResultList();
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
