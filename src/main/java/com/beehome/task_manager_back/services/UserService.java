package com.beehome.task_manager_back.services;

import com.beehome.task_manager_back.dto.UserRegistrationDTO;
import com.beehome.task_manager_back.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    public User registerUser(UserRegistrationDTO dto) {
//        // Validações e criptografia de senha
//        if (userRepository.existsByEmail(dto.getEmail())) {
//            throw new IllegalArgumentException("E-mail já registrado");
//        }
//
//        User user = new User();
//        user.setUsername(dto.getUsername());
//        user.setEmail(dto.getEmail());
//        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
//
//        return userRepository.save(user);
//    }
}
