package ru.job4j.mailnotify;

public class User {
    private String username;
    private String email;

    public User(String name, String email) {
        this.username = name;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
