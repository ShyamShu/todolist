package com.example.TO_Do_List.To_Do_List.Services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TO_Do_List.To_Do_List.Entity.Task;
import com.example.TO_Do_List.To_Do_List.Entity.User;
import com.example.TO_Do_List.To_Do_List.Repository.TaskRepo;

@Service
public class TaskService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TaskRepo taskRepo;

    public Task findByUser(String id) {
         return  taskRepo.findById(id).orElseThrow();
    }

    public Task saveTask(Task task) {
        String id = UUID.randomUUID().toString();
        task.setId(id);
        return taskRepo.save(task);
    }

    public void delete(Task task) {
        taskRepo.delete(task);
    }

    public List<Task> findByUser(User authenticatedUser) {
        List<Task> tasks = taskRepo.findByUser(authenticatedUser);
        //logger.info("list of task are : {}" , tasks.toString());
        

        return tasks;
    }

    public Task findById(String id) {
        Task task =  taskRepo.findById(id).orElseThrow() ;
        if(task != null)
        {
            return task;
    
        }
        return null;
    }
    
}
