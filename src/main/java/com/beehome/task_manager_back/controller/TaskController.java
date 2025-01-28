package com.beehome.task_manager_back.controller;

import com.beehome.task_manager_back.dto.TaskDTO;
import com.beehome.task_manager_back.dto.TaskFilterDTO;
import com.beehome.task_manager_back.enums.TaskStatus;
import com.beehome.task_manager_back.exception.ResourceNotFoundException;
import com.beehome.task_manager_back.models.TaskModel;
import com.beehome.task_manager_back.models.UserModel;
import com.beehome.task_manager_back.repository.TaskRepository;
import com.beehome.task_manager_back.repository.UserRepository;
import com.beehome.task_manager_back.services.TaskService;
import com.beehome.task_manager_back.services.UserService;
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
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private UserService userService;



    @GetMapping("")
    public ResponseEntity<List<TaskFilterDTO>> listTasksAll() {
        // Chama o serviço para obter todas as tarefas
        List<TaskFilterDTO> taskAll = taskService.getAllTasks();

        return ResponseEntity.status(HttpStatus.OK).body(taskAll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listTaskOne(@PathVariable(value = "id") UUID id) {
        try {
            TaskFilterDTO taskFilterDTO = taskService.getTaskById(id);
            return ResponseEntity.status(HttpStatus.OK).body(taskFilterDTO);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/filter/{status}")
    public ResponseEntity<Object> listTasksOne(@PathVariable(value = "status") TaskStatus status) {
        List<TaskFilterDTO> taskStatus = taskService.getTasksByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(taskStatus);
    }


    @PostMapping("")
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

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable UUID id, @RequestBody @Valid TaskDTO taskDTO, @RequestParam UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(id, taskDTO, userId));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id, @RequestParam UUID userId) {
        // Chama o serviço para deletar a tarefa
        boolean isDeleted = taskService.deleteTask(id, userId);

        if (isDeleted) {
            // Se a tarefa for deletada com sucesso, retorna o status 204 (No Content)
            return ResponseEntity.noContent().build();
        } else {
            // Se a tarefa não foi encontrada ou o usuário não tiver permissão, retorna 404 (Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
