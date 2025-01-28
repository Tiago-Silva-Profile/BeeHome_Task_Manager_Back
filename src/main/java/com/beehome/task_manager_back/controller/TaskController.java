package com.beehome.task_manager_back.controller;

import com.beehome.task_manager_back.dto.TaskDTO;
import com.beehome.task_manager_back.models.TaskModel;
import com.beehome.task_manager_back.repository.TaskRepository;
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

    @GetMapping("/task")
    public ResponseEntity<List<TaskModel>>  listTasksAll() {
        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.findAll());
    }

//    @GetMapping("/tasks/filter?status={status}")
//
    @GetMapping("/task/{id}")
    public ResponseEntity<Object> listTasksOne(@PathVariable(value="id") UUID id){
        Optional<TaskModel> taskOne = taskRepository.findById(id);
        if(taskOne.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskOne.get());
    }


    @PostMapping("/task")
    public ResponseEntity<TaskModel> createTask(@RequestBody @Valid TaskDTO taskDTO, @RequestParam Long userId) {
        var taskModel = new TaskModel();
        BeanUtils.copyProperties(taskDTO, taskModel);
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
