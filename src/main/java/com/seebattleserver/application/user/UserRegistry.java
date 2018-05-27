package com.seebattleserver.application.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRegistry {

    private List<User> users;

    public UserRegistry() {
        users = new ArrayList<>();
    }

    public void add(User user) {
        users.add(user);
    }

    public void remove(User user) {
        users.remove(users);
    }

    public boolean contains(User user) {
        return users.contains(user);
    }

    public List<User> getUsers() {
        return users;
    }

}
