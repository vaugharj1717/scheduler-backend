package com.DAOs;

import com.Entities.Department;
import com.Entities.Department;
import com.Entities.Department;
import com.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class DepartmentDAOImplTest {

    @Autowired
    private DepartmentDAO departmentDAO;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        List<Department> departments = (List<Department>) departmentDAO.getAll();
        assert departments.size() == 1;
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Transactional
    public void testRemove() throws Exception {
        List<Department> departments = (List<Department>) departmentDAO.getAll();
        departmentDAO.removeDepartment(departments.get(0).getId());

        em.flush();
        for(Department department : departments) em.detach(department);

        Department department = departmentDAO.getById(departments.get(0).getId());
    }

    @Test
    @Transactional
    public void testSaveOrUpdate() throws Exception {
        Department department = new Department();
        department = departmentDAO.saveOrUpdate(department);

        em.flush();
        em.detach(department);

        Department resultDepartment = departmentDAO.getById(department.getId());
        assert resultDepartment.equals(department);
    }

    @Test
    @Transactional
    public void testGetById() throws Exception {
        List<Department> departments = (List<Department>) departmentDAO.getAll();
        Department department = departmentDAO.getById(departments.get(0).getId());
        assert departments.size() == 1;
        assert department.equals(departments.get(0));
    }
}
