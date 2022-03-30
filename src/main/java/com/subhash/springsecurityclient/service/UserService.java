package com.subhash.springsecurityclient.service;

import com.subhash.springsecurityclient.entity.User;
import com.subhash.springsecurityclient.model.UserModel;

public interface UserService {

    User userRegistration(UserModel userRegistrationBody);

    void saveVerificationTokenForUser(User user, String token);

    boolean verifyUserRegistration(String token);
}
