package com.example.account.response;

import com.example.account.entity.User;

public class UserResponseBody {
    private String message;
    private User user;

    public UserResponseBody(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public UserResponseBody(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
