package com.beehome.task_manager_back.services;

import com.beehome.task_manager_back.dto.TaskDTO;
import com.beehome.task_manager_back.dto.TaskFilterDTO;
import com.beehome.task_manager_back.enums.TaskStatus;
import com.beehome.task_manager_back.exception.ResourceNotFoundException;
import com.beehome.task_manager_back.exception.UnauthorizedException;
import com.beehome.task_manager_back.models.TaskModel;
import com.beehome.task_manager_back.models.UserModel;
import com.beehome.task_manager_back.repository.TaskRepository;
import com.beehome.task_manager_back.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;




    public List<TaskFilterDTO> getAllTasks() {
        // Busca todas as tarefas
        List<TaskModel> tasks = taskRepository.findAll();

        // Mapeia as tarefas para DTOs
        return tasks.stream().map(task -> {
            TaskFilterDTO dto = new TaskFilterDTO();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus());
            dto.setDeadline(task.getDeadline());
            dto.setCreatedOn(task.getCreatedOn());
            dto.setAssignedToUsername(task.getAssignedTo().getUsername()); // Extrai o nome do usuário
            return dto;
        }).collect(Collectors.toList());
    }

    public TaskFilterDTO getTaskById(UUID id) {
        // Busca a tarefa pelo ID
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        // Mapeia a tarefa para DTO
        TaskFilterDTO taskFilterDTO = new TaskFilterDTO();
        taskFilterDTO.setId(task.getId());
        taskFilterDTO.setTitle(task.getTitle());
        taskFilterDTO.setDescription(task.getDescription());
        taskFilterDTO.setStatus(task.getStatus());
        taskFilterDTO.setDeadline(task.getDeadline());
        taskFilterDTO.setCreatedOn(task.getCreatedOn());
        taskFilterDTO.setAssignedToUsername(task.getAssignedTo().getUsername()); // Apenas o nome do usuário

        return taskFilterDTO;
    }

    public List<TaskFilterDTO> getTasksByStatus(TaskStatus status) {
        List<TaskModel> tasks;

        if (status != null) {
            tasks = taskRepository.findByStatus(status);
        } else {
            tasks = taskRepository.findAll();
        }

        // Mapeia as tarefas para DTOs
        return tasks.stream().map(task -> {
            TaskFilterDTO taskFilterDTO = new TaskFilterDTO();
            taskFilterDTO.setId(task.getId());
            taskFilterDTO.setTitle(task.getTitle());
            taskFilterDTO.setDescription(task.getDescription());
            taskFilterDTO.setStatus(task.getStatus());
            taskFilterDTO.setDeadline(task.getDeadline());
            taskFilterDTO.setCreatedOn(task.getCreatedOn());
            taskFilterDTO.setAssignedToUsername(task.getAssignedTo().getUsername());
            return taskFilterDTO;
        }).collect(Collectors.toList());
    }

    public TaskFilterDTO updateTask(UUID id, TaskDTO taskDTO, UUID userId) {
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setDeadline(taskDTO.getDeadline());
        task.setAssignedTo(user);

        return mapToDTO(taskRepository.save(task));
    }

    private TaskFilterDTO mapToDTO(TaskModel task) {
        TaskFilterDTO dto = new TaskFilterDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setDeadline(task.getDeadline());
        dto.setCreatedOn(task.getCreatedOn());
        dto.setAssignedToUsername(task.getAssignedTo().getUsername());
        return dto;
    }

    public boolean deleteTask(UUID id, UUID userId) {
        Optional<TaskModel> taskOpt = taskRepository.findById(id);

        if (taskOpt.isPresent()) {
            TaskModel task = taskOpt.get();

            // Verifique se o usuário tem permissão para excluir a tarefa (dependendo da sua lógica de negócios)
            if (task.getAssignedTo().getId().equals(userId)) {
                taskRepository.delete(task);  // Deleta a tarefa do banco
                return true;
            } else {
                throw new UnauthorizedException("User does not have permission to delete this task");
            }
        } else {
            return false;  // Caso a tarefa não seja encontrada
        }
    }

}
