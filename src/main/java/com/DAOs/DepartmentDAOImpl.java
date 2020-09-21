package com.DAOs;

import com.Entities.Department;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public void remove(Department department){
        em.remove(department);
    }
}
