package com.subhash.springsecurityclient.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class UserVerificationToken {

    @Id
    @SequenceGenerator(
            name="verification_token_sequence",
            sequenceName = "verification_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "verification_token_sequence")
    private Long id;
    private String token;
    private Date expirationTime;

    private static final int EXPIRE_TIME =10;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id",
             nullable = false,
            foreignKey = @ForeignKey(name="FK_USER_VERIFY_TOKEN")
    )
    private User user;

    public UserVerificationToken( User user, String token) {
        super();
        this.user=user;
        this.token=token;
        this.expirationTime = calculateExpirationTime(EXPIRE_TIME);
    }
    public UserVerificationToken(String token ) {
        super();
        this.token= token;
        this.expirationTime = calculateExpirationTime(EXPIRE_TIME);
    }


    private Date calculateExpirationTime(int expire_time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expire_time);
        return  new Date(calendar.getTime().getTime());
    }
}
