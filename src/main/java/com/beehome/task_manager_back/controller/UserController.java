package com.beehome.task_manager_back.controller;

import com.beehome.task_manager_back.dto.TaskDTO;
import com.beehome.task_manager_back.dto.UserRegistrationDTO;
import com.beehome.task_manager_back.models.TaskModel;
import com.beehome.task_manager_back.models.UserModel;
import com.beehome.task_manager_back.repository.UserRepository;
import com.beehome.task_manager_back.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public ResponseEntity<UserModel> createTask(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRegistrationDTO, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
    }
}
