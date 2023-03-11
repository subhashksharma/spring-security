package com.subhash.springsecurityclient.controller;


import com.subhash.springsecurityclient.entity.User;
import com.subhash.springsecurityclient.event.UserCreatedEvent;
import com.subhash.springsecurityclient.model.AuthenticationRequest;
import com.subhash.springsecurityclient.model.AuthenticationResponse;
import com.subhash.springsecurityclient.model.UserModel;
import com.subhash.springsecurityclient.service.AuthenticationService;
import com.subhash.springsecurityclient.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/unsecure/api")
public class UserRegistrationController {

    private  final UserService userService;

    private final ApplicationEventPublisher  applicationEventPublisher;

    private final AuthenticationService authenticationService;

    public UserRegistrationController(UserService userService, ApplicationEventPublisher applicationEventPublisher, AuthenticationService authenticationService) {
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userRegistrationBody, HttpServletRequest request) {

        User user = userService.userRegistration(userRegistrationBody);
        System.out.println("Context path =>>" +  request.getRequestURL().toString());
        applicationEventPublisher.publishEvent(new UserCreatedEvent(
                user,
                request.getRequestURL().toString()
        ));
        return  "new ResponseEntity<>()";
    }

    @GetMapping("/register/verifyRegistration")
    public boolean verifyRegistration(@RequestParam("token") String token) {
        boolean verify =userService.verifyUserRegistration(token);
        return verify;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest authenticationRequest) {

        return  ResponseEntity.ok(authenticationService.authenticateUser(authenticationRequest));
    }



}
