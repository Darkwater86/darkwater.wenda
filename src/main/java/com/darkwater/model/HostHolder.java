package com.darkwater.model;

import org.springframework.stereotype.Component;

/**
 * Created by lenovo1 on 2017/2/7.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUsers(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
