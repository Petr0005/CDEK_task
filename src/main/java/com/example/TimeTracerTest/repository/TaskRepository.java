package com.example.TimeTracerTest.repository;

import com.example.TimeTracerTest.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
