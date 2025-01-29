package com.beehome.task_manager_back.models;

import com.beehome.task_manager_back.dto.UserRegistrationDTO;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Entity
@Table(name = "BH_TB_USERS")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public UserModel() {
    }

    public UserModel(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserModel(UserRegistrationDTO user) {
        BeanUtils.copyProperties(user, this);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
