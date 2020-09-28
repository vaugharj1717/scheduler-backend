package com.controllers;


import com.Controllers.DepartmentController;
import com.Entities.Department;
import com.Services.DepartmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    @Test
    public void testList() throws Exception{
        //define return value of dependency
        List<Department> departments = new ArrayList<>();
        Department department1 = new Department();
        Department department2 = new Department();
        department1.setDepartmentName("department1");
        department2.setDepartmentName("department2");
        ResponseEntity<List<Department>> expectedResponse = new ResponseEntity<List<Department>>(departments, HttpStatus.OK);
        when(departmentService.getAllDepartments()).thenReturn(departments);

        //perform test
        String response = mockMvc.perform(get("/department"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(response);
        System.out.println(expectedResponse);
    }


}
