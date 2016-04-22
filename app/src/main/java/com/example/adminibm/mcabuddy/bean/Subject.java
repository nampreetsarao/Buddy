package com.example.adminibm.mcabuddy.bean;

import java.util.List;

/**
 * Created by ADMINIBM on 4/21/2016.
 */
public class Subject {
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String pwd;
    private List<String> roles;
    private List<String> aoe;

    public Subject(String fname, String lname, String email, String phone, String pwd, List<String> roles, List<String> aoe) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.pwd = pwd;
        this.roles = roles;
        this.aoe = aoe;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getAoe() {
        return aoe;
    }

    public void setAoe(List<String> aoe) {
        this.aoe = aoe;
    }
}
