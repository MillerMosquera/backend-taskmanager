package com.example.backendtaskmanager.Controller;


import com.example.backendtaskmanager.Entity.Task;
import com.example.backendtaskmanager.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{taskId}")
    public ResponseEntity<?> findById(@PathVariable Long taskId) {
        List<Task> taskList = this.taskService.findById(taskId);
        return ResponseEntity.ok(taskList);
    }

    @GetMapping
    public ResponseEntity<?> findByUserId(@RequestParam("userId") Long userId) {
        List<Task> taskList = this.taskService.findByUserId(userId);
        return ResponseEntity.ok(taskList);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestParam("userId") Long userId) {
        Task createTask = taskService.createTask(task, userId);
        return ResponseEntity.ok(createTask);
    }

    @PutMapping
    public ResponseEntity<Task> updateTask(@RequestBody Task task, @RequestParam("userId") Long taskId) {
        Task updateTask = taskService.updateTask(task, taskId);
        return ResponseEntity.ok(updateTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }
}
