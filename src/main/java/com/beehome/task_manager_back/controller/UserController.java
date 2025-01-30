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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserRegistrationDTO> listarTodos(){
        return userService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        userService.inserir(userRegistrationDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public UserRegistrationDTO alterar(@RequestBody UserRegistrationDTO usuario) {
        return userService.alterar(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") UUID id){
        userService.excluir(id);
        return ResponseEntity.ok().build();
    }


}
