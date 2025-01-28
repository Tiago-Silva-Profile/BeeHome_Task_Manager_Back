package com.beehome.task_manager_back.controller;

import com.beehome.task_manager_back.dto.TaskDTO;
import com.beehome.task_manager_back.dto.TaskFilterDTO;
import com.beehome.task_manager_back.models.TaskModel;
import com.beehome.task_manager_back.repository.TaskRepository;
import com.beehome.task_manager_back.repository.UserRepository;
import com.beehome.task_manager_back.services.TaskService;
import jakarta.validation.Valid;
import org.hibernate.query.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;



    @GetMapping("/task")
    public ResponseEntity<List<TaskFilterDTO>> listTasksAll() {
        List<TaskModel> tasks = taskRepository.findAll();

        // Mapeie as entidades para DTOs
        List<TaskFilterDTO> taskAll = tasks.stream().map(task -> {
            TaskFilterDTO dto = new TaskFilterDTO();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus());
            dto.setDeadline(task.getDeadline());
            dto.setCreatedOn(task.getCreatedOn());
            dto.setAssignedToUsername(task.getAssignedTo().getUsername()); // Extrai o nome do usuário
            return dto;
        }).toList();

        return ResponseEntity.status(HttpStatus.OK).body(taskAll);
    }


//    @GetMapping("/tasks/filter?status={status}")
//
    @GetMapping("/task/{id}")
    public ResponseEntity<Object> listTasksOne(@PathVariable(value="id") UUID id){
        Optional<TaskModel> taskOne = taskRepository.findById(id);
        if(taskOne.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        TaskModel task = taskOne.get();

        // Mapear para DTO
        TaskFilterDTO taskFilterDTO = new TaskFilterDTO();
        taskFilterDTO.setId(task.getId());
        taskFilterDTO.setTitle(task.getTitle());
        taskFilterDTO.setDescription(task.getDescription());
        taskFilterDTO.setStatus(task.getStatus());
        taskFilterDTO.setDeadline(task.getDeadline());
        taskFilterDTO.setCreatedOn(task.getCreatedOn());
        taskFilterDTO.setAssignedToUsername(task.getAssignedTo().getUsername()); // Apenas o nome do usuário

        return ResponseEntity.status(HttpStatus.OK).body(taskFilterDTO);
    }


    @PostMapping("/task")
    public ResponseEntity<TaskModel> createTask(@RequestBody @Valid TaskDTO taskDTO, @RequestParam UUID userId) {

        // Busca o usuário no banco de dados
        var userModel = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskDTO, taskModel);

        // Define o usuário responsável e salva a tarefa
        taskModel.setAssignedTo(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(taskModel));
    }

//    @PutMapping("/task/{id}")
//    public Task updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO, @RequestParam Long userId) {
//        return taskService.updateTask(id, taskDTO, userId);
//    }
//
//    @DeleteMapping("/task/{id}")
//    public void deleteTask(@PathVariable Long id, @RequestParam Long userId) {
//        taskService.deleteTask(id, userId);
//    }
}
