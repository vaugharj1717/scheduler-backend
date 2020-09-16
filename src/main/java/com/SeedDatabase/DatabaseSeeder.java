package com.SeedDatabase;

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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
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
}
