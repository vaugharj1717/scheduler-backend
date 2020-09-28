package com.DAOs;

import com.Entities.Department;
import com.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class DepartmentDAOImplTest {

    @Autowired
    private DepartmentDAO departmentDao;


    @Test
    public void testGetAll() throws Exception {
        List<Department> departments = (List<Department>) departmentDao.getAll();

        assert departments.size() == 1;

    }
}
