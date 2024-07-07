package com.example.account.service;

import com.example.account.exception.GetUserException;
import com.example.account.repository.UserRepository;
import com.example.account.response.UserResponseBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@SpringBootTest
class UserServiceDeleteTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void deleteUserSuccess() {
        String userId = "usertest";
        when(userRepository.existsById(userId)).thenReturn(true);
        UserResponseBody response = userService.delete(userId);

        assertEquals("User successfully deleted", response.getMessage());
        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUserNotFound() {
        String userId = "usertest";
        when(userRepository.existsById(userId)).thenReturn(false);
        GetUserException exception = assertThrows(GetUserException.class, () -> {
            userService.delete(userId);
        });
        assertEquals("No User Found", exception.getMessage());
        verify(userRepository).existsById(userId);
        verify(userRepository, times(0)).deleteById(userId);
    }
}
