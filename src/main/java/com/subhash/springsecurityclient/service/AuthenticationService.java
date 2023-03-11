package com.subhash.springsecurityclient.service;

import com.subhash.springsecurityclient.entity.User;
import com.subhash.springsecurityclient.model.AuthenticationRequest;
import com.subhash.springsecurityclient.model.AuthenticationResponse;
import com.subhash.springsecurityclient.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private  PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest)  {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(authenticationRequest.getEmail());

        String jwtToken = jwtService.generateJwtToken(user);

        return  AuthenticationResponse.builder()
                .token(jwtToken).build();
    }
}
