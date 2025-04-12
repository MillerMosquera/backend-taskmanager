package com.example.backendtaskmanager.Service.Implement;

import com.example.backendtaskmanager.Entity.Task;
import com.example.backendtaskmanager.Entity.User;
import com.example.backendtaskmanager.Repository.TaskRepository;
import com.example.backendtaskmanager.Repository.UserRepository;
import com.example.backendtaskmanager.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Task> findById(Long taskId) {
        try {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isEmpty()) {
                log.warn("Task with id not found");
            }
            Task result = optionalTask.get();
            return List.of(result);
        } catch (Exception e) {
            throw new RuntimeException("Error finding task by id" + taskId, e);
        }
    }

    @Override
    public List<Task> findByUserId(Long userId) {
        try {
            return taskRepository.findAllByUserIdOrderByDateAsc(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error finding task by user id" + userId, e);
        }
    }

    @Override
    public Task createTask(Task task, Long userId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                log.warn("Trying to create a task with non-existent userId");
                throw new IllegalArgumentException("User with id not found" + userId);
            }
            if (task.getId() != null) {
                log.warn("Trying to create a task with id");
                throw new IllegalArgumentException("Task with id not found" + task.getId());
            }
            User result = optionalUser.get();
            task.setUser(result);
            return taskRepository.save(task);
        } catch (Exception e) {
            log.error("Error creating task", e);
            throw new RuntimeException("Error creating task" + e.getMessage());
        }

    }

    @Override
    public Task updateTask(Task task, Long userId) {
        try {
            Optional<User> optionalUpdateUser = userRepository.findById(userId);
            if (optionalUpdateUser.isEmpty()) {
                log.warn("Trying to update a task with non-existent userId");
                throw new IllegalArgumentException("User with id not found" + userId);
            }
            if (task.getId() == null) {
                log.warn("Trying to update a task with non-existent taskId");
                throw new IllegalArgumentException("Task with id not found" + task.getId());
            }
            if (!taskRepository.existsById(task.getId())) {
                log.warn("Trying to update a task with non-existent taskId");
                throw new IllegalArgumentException("Task with id not found" + task.getId());
            }
            User user = optionalUpdateUser.get();
            task.setUser(user);
            Task result = taskRepository.save(task);

            return result;
        } catch (Exception e) {
            log.error("Error updating task", e);
            throw new RuntimeException("Error updating task" + e.getMessage());
        }
    }

    @Override
    public void deleteTask(Long taskId) {
        try {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isEmpty()) {
                log.warn("Trying to delete a task with non-existent taskId");
                throw new IllegalArgumentException("Task with id not found" + taskId);
            }
            taskRepository.deleteById(taskId);
        } catch (Exception e) {
            log.error("Error deleting task", e);
            throw new RuntimeException("Error deleting task" + e.getMessage());
        }
    }
}
