package com.example.TO_Do_List.To_Do_List.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TO_Do_List.To_Do_List.Entity.Task;
import com.example.TO_Do_List.To_Do_List.Entity.User;

@Repository
public interface TaskRepo extends JpaRepository<Task , String> {
    List<Task> findByUser(User user);
    
}
