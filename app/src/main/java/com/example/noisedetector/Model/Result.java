package com.example.noisedetector.Model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by Krzysiek on 14.10.2018.
 */

public class Result implements Serializable {

    private ArrayList<Long> timeSnapshots;
    private long date;
    private String loginPerson;

    public Result(long date,ArrayList<Long> snapTimes, String login){
        this.timeSnapshots = snapTimes;
        this.date = date;
        this.loginPerson = login;
    }
    public ArrayList<Long> getTimeSnapshots() {
        return timeSnapshots;
    }

    public void setTimeSnapshots(ArrayList<Long> timeSnapshots) {
        this.timeSnapshots = timeSnapshots;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLoginPerson() {
        return loginPerson;
    }

    public void setLoginPerson(String loginPerson) {
        this.loginPerson = loginPerson;
    }
}
