package com.example.TO_Do_List.To_Do_List.Services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TO_Do_List.To_Do_List.Entity.User;
import com.example.TO_Do_List.To_Do_List.Repository.UserRepo;

@Service
public class UserServices {

    @Autowired
    UserRepo userRepo;

    public void saveUser(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);
        userRepo.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
    
}
