package com.example.TO_Do_List.To_Do_List.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.TO_Do_List.To_Do_List.Entity.User;
import com.example.TO_Do_List.To_Do_List.Repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByUsername(username);

        return User.builder()
              .username(user.getUsername())
              .password(user.getPassword())  // Assuming the password is already encoded
              .build();


        
    }

}
