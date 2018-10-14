package com.example.noisedetector.Model;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * Created by Krzysiek on 09.10.2018.
 */

public class User implements Serializable {


    private Long id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
