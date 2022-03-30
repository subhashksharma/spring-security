package com.subhash.springsecurityclient.event;

import com.subhash.springsecurityclient.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserCreatedEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;
    public UserCreatedEvent(User user, String applicationUrl) {
        super(user);
        this.user=user;
        this.applicationUrl= applicationUrl;
    }
}
