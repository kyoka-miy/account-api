package com.example.account.service;

import com.example.account.entity.User;
import com.example.account.exception.CreateUserException;
import com.example.account.exception.GetUserException;
import com.example.account.repository.UserRepository;
import com.example.account.response.UserResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserResponseBody add(User user) {
        if(userRepository.existsById(user.getUser_id())) {
            throw new CreateUserException("already same user_id is used");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return new UserResponseBody("Account successfully created", user);
    }

    @Override
    public UserResponseBody get(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new GetUserException("No User Found");
        }
        if(user.get().getNickname() == null)
            user.get().setNickname(user.get().getUser_id());
        return new UserResponseBody("User details by user_id", user.get());
    }

    @Override
    public UserResponseBody update(String userId, User user) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new GetUserException("No User Found");
        }
        User targetUser = optionalUser.get();
        if(user.getNickname() != null && !user.getNickname().isEmpty())
            targetUser.setNickname(user.getNickname());
        if(user.getComment() != null && !user.getComment().isEmpty())
            targetUser.setComment(user.getComment());
        return new UserResponseBody("User successfully updated", userRepository.save(targetUser));
    }

    @Override
    public UserResponseBody delete(String userId) {
        if(!userRepository.existsById(userId)) {
            throw new GetUserException("No User Found");
        }
        userRepository.deleteById(userId);
        return new UserResponseBody("User successfully deleted");
    }
}
