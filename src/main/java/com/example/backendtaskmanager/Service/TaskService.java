package com.example.backendtaskmanager.Service;

import com.example.backendtaskmanager.Entity.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {
    public List<Task> findById(Long taskId);

    public List<Task> findByUserId(Long userId);

    public Task createTask(Task task, Long userId);

    public Task updateTask(Task task, Long userId);

    public void deleteTask(Long taskId);
}
