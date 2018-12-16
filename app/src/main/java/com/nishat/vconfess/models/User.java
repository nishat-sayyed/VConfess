package com.nishat.vconfess.models;

import java.util.Map;

/**
 * Created by Nishat on 9/20/2017.
 */

public class User {

    private String department;
    private String year;
    private String division;
    private String gender;
    private Map<String, Confession> confession;
    private String uid;
    private Confession confess;

    public User(){};

    public User(String department, String year, String division, String gender, Map<String, Confession> confession) {
        this.department = department;
        this.year = year;
        this.division = division;
        this.gender = gender;
        this.confession = confession;
    }

    public Confession getConfess() {
        return confess;
    }

    public void setConfess(Confession confess) {
        this.confess = confess;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Map<String, Confession> getConfession() {
        return confession;
    }

    public void setConfession(Map<String, Confession> confession) {
        this.confession = confession;
    }
}

