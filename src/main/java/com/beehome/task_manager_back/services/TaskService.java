package com.beehome.task_manager_back.services;

import com.beehome.task_manager_back.dto.TaskDTO;
import com.beehome.task_manager_back.repository.TaskRepository;
import com.beehome.task_manager_back.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Date;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

//    public Page<Task> listTasks(Pageable pageable) {
//        return taskRepository.findAll(pageable);
//    }
//
//    public Task createTask(TaskDTO taskDTO, Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
//
//        Task task = new Task();
//        task.setTitle(taskDTO.getTitle());
//        task.setDescription(taskDTO.getDescription());
//        task.setStatus(taskDTO.getStatus());
//        task.setDeadline(taskDTO.getDeadline());
//        task.setCreatedOn(new Date());
//        task.setAssignedTo(user);
//
//        return taskRepository.save(task);
//    }
//
//    public Task updateTask(Long id, TaskDTO taskDTO, Long userId) {
//        Task task = taskRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
//
//        if (!task.getAssignedTo().getId().equals(userId)) {
//            throw new AccessDeniedException("Usuário não autorizado a editar esta tarefa");
//        }
//
//        task.setTitle(taskDTO.getTitle());
//        task.setDescription(taskDTO.getDescription());
//        task.setStatus(taskDTO.getStatus());
//        task.setDeadline(taskDTO.getDeadline());
//
//        return taskRepository.save(task);
//    }
//
//    public void deleteTask(Long id, Long userId) {
//        Task task = taskRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
//
//        if (!task.getAssignedTo().getId().equals(userId)) {
//            throw new AccessDeniedException("Usuário não autorizado a excluir esta tarefa");
//        }
//
//        taskRepository.delete(task);
//    }
}
