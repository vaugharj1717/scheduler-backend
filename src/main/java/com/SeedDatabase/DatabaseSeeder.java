package com.SeedDatabase;

import com.DAOs.DepartmentDAO;
import com.Entities.Department;
import com.Entities.Position;
import com.Entities.User;
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
            userService.registerUser(newUser);
        }

    }

    private void loadDepartments(){
        Department newDepartment = new Department();
        Position newPosition = new Position();
        List<Position> positionList = new ArrayList<Position>();
        positionList.add(newPosition);
        newDepartment.setName("testName");
        newDepartment.setPositionList(positionList);
        departmentDAO.saveDepartment(newDepartment);
    }
}
