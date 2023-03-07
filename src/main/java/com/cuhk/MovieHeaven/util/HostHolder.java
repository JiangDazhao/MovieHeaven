package com.cuhk.MovieHeaven.util;

import com.cuhk.MovieHeaven.entity.User;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    public static ThreadLocal<User>  users=new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
