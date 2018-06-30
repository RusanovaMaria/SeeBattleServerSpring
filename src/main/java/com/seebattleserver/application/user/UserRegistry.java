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

    public User getUserByName(String name) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        throw new IllegalArgumentException("Пользователь с таким именем не обнаружен");
    }
}
