package com.SeedDatabase;

import com.DAOs.DepartmentDAO;
import com.DAOs.ScheduleDAO;
import com.Entities.*;
import com.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    UserService userService;

    @Autowired
    DepartmentDAO departmentDAO;

    @Autowired
    ScheduleDAO scheduleDAO;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadDepartments();
    }


    private void loadDepartments(){
        Department newDepartment = new Department();
        newDepartment.setDepartmentName("testDepartmentName");
        Position newPosition = new Position();
        newPosition.setPositionName("testPositionName");
        newDepartment.addPosition(newPosition);
        departmentDAO.saveDepartment(newDepartment);
    }
}

