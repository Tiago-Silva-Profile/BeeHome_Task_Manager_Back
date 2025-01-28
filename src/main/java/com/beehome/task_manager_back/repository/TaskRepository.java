package com.beehome.task_manager_back.repository;

import com.beehome.task_manager_back.enums.TaskStatus;
import com.beehome.task_manager_back.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository  extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByStatus(TaskStatus status);
}
