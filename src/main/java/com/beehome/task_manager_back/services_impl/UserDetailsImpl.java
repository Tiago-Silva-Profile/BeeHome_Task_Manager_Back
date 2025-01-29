package com.beehome.task_manager_back.services_impl;

import com.beehome.task_manager_back.models.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class UserDetailsImpl implements UserDetails {

    private UUID id;

    private String name;

    private String userName;

    private String password;

    private String email;

    public UserDetailsImpl(UUID id, String userName, String email, String password, Collection<? extends GrantedAuthority> autorities) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.autorities = autorities;
    }

    public static UserDetailsImpl build(UserModel userModel){
        return new UserDetailsImpl(
                userModel.getId(),
                userModel.getUsername(),
                userModel.getEmail(),
                userModel.getPassword(),
                new ArrayList<>());
    }

    private Collection<? extends GrantedAuthority> autorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
        return autorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
        return true;
    }
}
