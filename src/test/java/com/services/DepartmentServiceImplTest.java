package com.services;

import com.App;
import com.DAOs.DepartmentDAO;
import com.Entities.Department;
import com.Services.DepartmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class DepartmentServiceImplTest {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private DepartmentDAO departmentDao;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() throws Exception {
        //define return value of dependency
        List<Department> departments= new ArrayList<Department>();
        Department department1 = new Department();
        department1.setDepartmentName("department1");
        Department department2 = new Department();
        department2.setDepartmentName("department2");
        departments.add(department1);
        departments.add(department2);
        when(departmentDao.getAll()).thenReturn(departments);

        //perform test
        List<Department> ret = departmentService.getAllDepartments();
        assert ret.size() == 2;
        assert ret.get(0).getDepartmentName().equals("department1");
        assert ret.get(1).getDepartmentName().equals("department2");
    }
}
