package com.darkwater.model;

import java.util.Date;

/**
 * Created by lenovo1 on 2017/2/5.
 */
public class LoginTicket {

    private int id;
    private int userId;
    private Date empired;
    private int status;
    private String ticket;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getEmpired() {
        return empired;
    }
    public void setEmpired(Date empired) {
        this.empired = empired;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getTicket() {
        return ticket;
    }
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

}
