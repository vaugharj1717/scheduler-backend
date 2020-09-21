package com.SeedDatabase;

import com.DAOs.DepartmentDAO;
import com.DAOs.ScheduleDAO;
import com.Entities.*;
import com.Entities.Department;
import com.Entities.Position;
import com.Entities.User;
import com.Entities.enumeration.Role;
import com.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

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
        loadUsers();
    }

    private void loadUsers(){
        for(int i=1; i<=3; i++){
            User newUser = new User();
            newUser.setUsername("testName" + i);
            newUser.setPassword("testPassword" + i);
            newUser.setRole(Role.CANDIDATE);
            userService.registerUser(newUser);
        }

    }

    private void loadDepartments(){
        Department newDepartment = new Department();
        newDepartment.setDepartmentName("testDepartmentName");
        Position newPosition = new Position();
        newPosition.setPositionName("position1");
        Set<Position> positions = new HashSet<Position>();
        positions.add(newPosition);
        newPosition.setDepartment(newDepartment);
        newDepartment.setPositions(positions);
        departmentDAO.saveDepartment(newDepartment);
    }
}
