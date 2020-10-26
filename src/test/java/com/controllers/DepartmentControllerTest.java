package com.controllers;


import com.App;
import com.Controllers.DepartmentController;
import com.Entities.Department;
import com.Services.DepartmentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
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
    public void testGetAllDepartments() throws Exception{
        //define return value of dependency
        List<Department> departments = new ArrayList<>();
        Department department1 = new Department();
        Department department2 = new Department();
        department1.setDepartmentName("department1");
        department2.setDepartmentName("department2");
        departments.add(department1);
        departments.add(department2);
        when(departmentService.getAllDepartments()).thenReturn(departments);

        //define expected value
        ObjectWriter ow = new ObjectMapper().writer();
        String expectedJson = ow.writeValueAsString(departments);


        //perform test
        String response = mockMvc.perform(get("/department"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assert response.equals(expectedJson);
    }

    @Test
    public void testDeleteDepartment() throws Exception{
        //define return value of dependency
        doNothing().when(departmentService).deleteDepartment(1);
        String expectedResponse = "1";

        //perform test
        String response = mockMvc.perform(delete("/department/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assert(response.equals(expectedResponse));
    }


}
