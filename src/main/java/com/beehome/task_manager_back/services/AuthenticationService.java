package com.beehome.task_manager_back.services;

import com.beehome.task_manager_back.dto.AcessDTO;
import com.beehome.task_manager_back.dto.AuthenticationDTO;
import com.beehome.task_manager_back.security.jwt.JwtUtil;
import com.beehome.task_manager_back.services_impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AcessDTO login(AuthenticationDTO authDto){

        try{
            //Busca mecanismo de credencial para o spring
            UsernamePasswordAuthenticationToken userAuth =
                    new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());

            //Prepara mecanismo paa autenticação
            Authentication authentication = authenticationManager.authenticate(userAuth);

            //Busca usuario Logado
            UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();

            String token = jwtUtil.generateTokenFromUserDetailsImpl(userAuthenticate);

            AcessDTO acessDTO = new AcessDTO(token);

            return acessDTO;

        }catch (BadCredentialsException e) {

        }

        return null;

    }

}
