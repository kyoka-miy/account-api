package com.example.account.controller;

import com.example.account.entity.User;
import com.example.account.exception.CreateUserException;
import com.example.account.exception.UpdateUserException;
import com.example.account.response.UserResponseBody;
import com.example.account.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@RestController
@RequestMapping("/")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService useService) {
        this.userService = useService;
    }

    @RequestMapping("")
    String home() {
        return "Hello World!";
    }
    @PostMapping("signup")
    public UserResponseBody createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (user.getPassword() != null && (user.getPassword().length() < 8 || user.getPassword().length() > 20)) {
            throw new CreateUserException("Password must be between 8 and 20 characters");
        }
        if(bindingResult.hasErrors()) {
            throw new CreateUserException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return userService.add(user);
    }

    @GetMapping("users/{userId}")
    public UserResponseBody getUser(@PathVariable String userId){
        return userService.get(userId);
    }

    @PutMapping("users/{userId}")
    public UserResponseBody updateUser(@PathVariable String userId, @RequestBody User user) throws AccessDeniedException {
        if(!userId.equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new AccessDeniedException("No Permission for Update");
        }
        if (user.getPassword() != null && (user.getPassword().length() < 8 || user.getPassword().length() > 20)) {
            throw new CreateUserException("Password must be between 8 and 20 characters");
        }
        if(user.getNickname() == null && user.getComment() == null) {
            throw new UpdateUserException("required nickname or comment");
        }
        if(user.getUser_id() != null || user.getPassword() != null) {
            throw new UpdateUserException("user_id and password are not updatable");
        }
        return userService.update(userId, user);
    }

    @DeleteMapping("users/{userId}")
    public UserResponseBody deleteUser(@PathVariable String userId) {
        return userService.delete(userId);
    }
}