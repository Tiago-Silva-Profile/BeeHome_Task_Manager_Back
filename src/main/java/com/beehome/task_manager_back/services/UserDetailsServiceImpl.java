package com.beehome.task_manager_back.services;

import com.beehome.task_manager_back.models.UserModel;
import com.beehome.task_manager_back.repository.UserRepository;
import com.beehome.task_manager_back.services_impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel usuario = userRepository.findByUsername(username).get();
        return UserDetailsImpl.build(usuario);
    }

}
