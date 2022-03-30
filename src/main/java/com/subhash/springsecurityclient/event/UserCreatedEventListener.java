package com.subhash.springsecurityclient.event;

import com.subhash.springsecurityclient.entity.User;
import com.subhash.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class UserCreatedEventListener implements ApplicationListener<UserCreatedEvent> {


    @Autowired
    private UserService  userService;

    @Override
    public void onApplicationEvent(UserCreatedEvent event) {
        // Created verification token for user Link;
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(user, token);

        String url = event.getApplicationUrl()+"/verifyRegistration?token="+token;
        log.info("Click this link to verify the user " + url);

    }
}
