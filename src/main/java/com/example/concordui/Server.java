package com.example.concordui;

import java.util.HashMap;
import java.util.Map;

public class Server {

    Map<String, String> passwords = new HashMap<>();

    public Server() {
        passwords.put("bob", "bob's password");
        passwords.put("alex", "alex's password");
    }

    public boolean login(String userName, String password) {
        if (!passwords.containsKey(userName)) return false;
        return passwords.get(userName).equals(password);
    }
}
