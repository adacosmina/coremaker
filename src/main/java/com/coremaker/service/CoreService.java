
package com.coremaker.service;

import com.coremaker.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CoreService {

    Map<String, User> users = new HashMap<>();
    Set<String> tokens = new HashSet<>();

    public String createUser(User user) {
        if (users.get(user.getEmail()) == null) {
            users.put(user.getEmail(), user);
            return "User created. Welcome, " + user.getUsername() + " :)";
        }
        return "Oh no, user already exists :(";
    }

    public Optional<User> getUser(String email) {
        return Optional.ofNullable(users.get(email));
    }

    public List<User> getAllAccounts() {
        return new ArrayList<>(users.values());
    }

    public void saveJwtToken(String jwtToken) {
        tokens.add(jwtToken);
    }

    public boolean checkIfTokenExists(String jwtToken) {
        return tokens.contains(jwtToken);
    }
}
