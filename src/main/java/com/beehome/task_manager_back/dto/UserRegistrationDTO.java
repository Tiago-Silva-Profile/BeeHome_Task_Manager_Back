package com.beehome.task_manager_back.dto;

import com.beehome.task_manager_back.models.UserModel;

import java.util.UUID;

public class UserRegistrationDTO {

    private UUID Id;
    private String username;
    private String email;
    private String password;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(UUID id, String username, String email) {
        this.username = username;
        this.email = email;
        this.Id = id;

    }

    public UserRegistrationDTO(UserModel user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }


    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
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
