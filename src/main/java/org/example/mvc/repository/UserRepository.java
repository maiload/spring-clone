package org.example.mvc.repository;

import org.example.mvc.annotation.Repository;
import org.example.mvc.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public void save(User user){
        users.put(user.getUserId(), user);
    }

    public Collection<User> findAll() {
        return users.values();
    }

    public User findByUserId(String userId) {
        return users.get(userId);
    }
}
