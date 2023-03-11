package com.subhash.springsecurityclient.controller;

import com.subhash.springsecurityclient.model.AuthenticationRequest;
import com.subhash.springsecurityclient.model.AuthenticationResponse;
import com.subhash.springsecurityclient.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secure/api")
public class AuthenticationController {

    @GetMapping("/hello")
    public String getHello(){
        return "Hello Security";
    }


}
