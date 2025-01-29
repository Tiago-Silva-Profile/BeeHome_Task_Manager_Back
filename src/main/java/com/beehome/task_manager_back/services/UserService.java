package com.beehome.task_manager_back.services;

import com.beehome.task_manager_back.dto.UserRegistrationDTO;
import com.beehome.task_manager_back.exception.ResourceNotFoundException;
import com.beehome.task_manager_back.models.UserModel;
import com.beehome.task_manager_back.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<UserRegistrationDTO> listarTodos() {
        List<UserModel> usuarios = userRepository.findAll();
        return usuarios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void inserir(UserRegistrationDTO user) {
        UserModel userModel = new UserModel(user);
        userModel.setPassword(passwordEncoder.encode(user.getPassword()));
        ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));

    }

    public UserRegistrationDTO alterar(UserRegistrationDTO userDTO) {
        UserModel usuario = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuario.setUsername(userDTO.getUsername());
        usuario.setEmail(userDTO.getEmail());

        userRepository.save(usuario);

        return convertToDTO(usuario);
    }

    public void excluir(UUID id) {
        UserModel user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

    private UserRegistrationDTO convertToDTO(UserModel user) {
        return new UserRegistrationDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

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
