package com.Integration.client.Boundaries;

public class User {
    String email;
    String role;
    String username;
    String avatar;

    public User() {
    }

    public User(String email, String role, String username, String avatar) {
        this.email = email;
        this.role = role;
        this.username = username;
        this.avatar = avatar;
    }
}
