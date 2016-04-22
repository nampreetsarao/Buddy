package com.example.adminibm.mcabuddy.bean;

/**
 * Created by ADMINIBM on 4/21/2016.
 */
public class Requester {

    private String accessToken;
    private String email;

    public Requester(String accessToken, String email) {
        this.accessToken = accessToken;
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
