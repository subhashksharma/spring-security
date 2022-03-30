package com.subhash.springsecurityclient.repository;

import com.subhash.springsecurityclient.entity.UserVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerificationRepository extends JpaRepository<UserVerificationToken, Long> {

    UserVerificationToken findByToken(String token);
}
