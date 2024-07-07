package com.example.account.service;

import com.example.account.entity.User;
import com.example.account.exception.GetUserException;
import com.example.account.repository.UserRepository;
import com.example.account.response.UserResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceGetTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @Test
    void GetUserSuccess() {
        User user = new User("usertest", "password");
        when(userRepository.findById("usertest")).thenReturn(Optional.of(user));

        UserResponseBody response = userService.get("usertest");
        assertEquals("User details by user_id", response.getMessage());
        assertEquals("usertest", response.getUser().getUser_id());
    }

    @Test
    void GetUserNotExist() {
        when(userRepository.findById("usertest")).thenReturn(Optional.empty());

        GetUserException exception = assertThrows(GetUserException.class, () -> {
            userService.get("usertest");
        });
        assertEquals("No User Found", exception.getMessage());
    }
}
