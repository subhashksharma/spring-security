package com.subhash.springsecurityclient.controller;


import com.subhash.springsecurityclient.entity.User;
import com.subhash.springsecurityclient.event.UserCreatedEvent;
import com.subhash.springsecurityclient.model.UserModel;
import com.subhash.springsecurityclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher  applicationEventPublisher;

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
    public String verifyRegistration(@RequestParam("token") String token) {
        boolean verify =userService.verifyUserRegistration(token);
        return "success";
    }




    @GetMapping("/hello")
    public String getHello(){
        return "Hello Security";
    }

}
