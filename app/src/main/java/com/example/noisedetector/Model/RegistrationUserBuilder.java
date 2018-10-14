package com.example.noisedetector.Model;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Krzysiek on 09.10.2018.
 */
@Accessors(chain = true)
public class RegistrationUserBuilder {
    @Setter
    private String userName;
    @Setter
    private boolean sex;
    @Setter
    private String email;
    @Setter
    private String password;




    public User build() {
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
