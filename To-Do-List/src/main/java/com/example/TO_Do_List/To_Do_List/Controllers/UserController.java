package com.example.TO_Do_List.To_Do_List.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TO_Do_List.To_Do_List.Entity.User;
import com.example.TO_Do_List.To_Do_List.Security.JwtUtil;
import com.example.TO_Do_List.To_Do_List.Services.UserServices;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class UserController {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServices userServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

   @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
    // Check if the username already exists
    if (userServices.existsByUsername(user.getUsername())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(Map.of("message", "Username already exists"));
    }
    
    // Encrypt the user's password
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    
    // Save the user
    userServices.saveUser(user);
    
    // Return a success message
    return ResponseEntity.status(HttpStatus.CREATED)
                         .body(Map.of("message", "User registered successfully"));
}

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        return jwtUtil.generateToken(user.getUsername());
    }
    
    
}
