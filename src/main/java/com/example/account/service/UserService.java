package com.example.account.service;

import com.example.account.entity.User;
import com.example.account.response.UserResponseBody;

public interface UserService {
    UserResponseBody add(User user);
    UserResponseBody get(String userId);
    UserResponseBody update(String userId, User user);
    UserResponseBody delete(String userId);
}
