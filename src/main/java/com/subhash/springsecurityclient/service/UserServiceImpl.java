package com.subhash.springsecurityclient.service;

import com.subhash.springsecurityclient.entity.User;
import com.subhash.springsecurityclient.entity.UserVerificationToken;
import com.subhash.springsecurityclient.model.UserModel;
import com.subhash.springsecurityclient.repository.UserRepository;
import com.subhash.springsecurityclient.repository.UserVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserVerificationRepository  userVerificationRepository;


    @Override
    public User userRegistration(UserModel userRegistrationBody) {

        User user = User.builder().email(userRegistrationBody.getEmail())
                        .firstName(userRegistrationBody.getFirstName())
                        .lastName(userRegistrationBody.getLastName())
                        .role("USER")
                        .password(passwordEncoder.encode(userRegistrationBody.getPassword())).build();
        ;
        return userRepository.save(user);
    }

    @Override
    public void saveVerificationTokenForUser(User user, String token) {
        UserVerificationToken verificationToken = new UserVerificationToken(user, token);
        userVerificationRepository.save(verificationToken);
    }

    @Override
    public boolean verifyUserRegistration(String token) {

        UserVerificationToken verificationToken = userVerificationRepository.findByToken(token);

        if(verificationToken == null) {
            return false;
        }
        else if (Calendar.getInstance().getTime().getTime() - verificationToken.getExpirationTime().getTime() <0) {
            userVerificationRepository.delete(verificationToken);
            User user = verificationToken.getUser();
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }else {
            return false;
        }
    }
}
