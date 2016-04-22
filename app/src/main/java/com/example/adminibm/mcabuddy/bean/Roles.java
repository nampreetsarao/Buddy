package com.example.adminibm.mcabuddy.bean;

import java.util.List;

/**
 * Created by ADMINIBM on 4/21/2016.
 */
public class Roles {
    private List<String> roles;

    public Roles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {

        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
