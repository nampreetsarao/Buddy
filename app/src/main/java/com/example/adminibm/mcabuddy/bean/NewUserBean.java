package com.example.adminibm.mcabuddy.bean;

/**
 * Created by ADMINIBM on 4/21/2016.
 */
public class NewUserBean {

    private Requester requester;
    private Subject subject;

    public NewUserBean(Requester requester, Subject subject) {
        this.requester = requester;
        this.subject = subject;
    }

    public Requester getRequester() {
        return requester;
    }

    public void setRequester(Requester requester) {
        this.requester = requester;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
