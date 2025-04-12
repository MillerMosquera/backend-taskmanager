package com.example.backendtaskmanager.Repository;

import com.example.backendtaskmanager.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUserIdOrderByDateAsc(Long userId);

}
