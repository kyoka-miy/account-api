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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest
class UserServiceUpdateTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void updateUserSuccess() {
        User existingUser = new User();
        existingUser.setUser_id("usertest");
        existingUser.setNickname("oldNickname");
        existingUser.setComment("oldComment");

        User updateUser = new User();
        updateUser.setNickname("newNickname");
        updateUser.setComment("newComment");

        when(userRepository.findById("usertest")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserResponseBody response = userService.update("usertest", updateUser);

        assertEquals("User successfully updated", response.getMessage());
        assertEquals("usertest", response.getUser().getUser_id());
        assertEquals("newNickname", response.getUser().getNickname());
        assertEquals("newComment", response.getUser().getComment());

        verify(userRepository).findById("usertest");
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUserNotFound() {
        User updateUser = new User();
        updateUser.setNickname("newNickname");
        updateUser.setComment("newComment");

        when(userRepository.findById("usertest")).thenReturn(Optional.empty());

        GetUserException exception = assertThrows(GetUserException.class, () -> {
            userService.update("usertest", updateUser);
        });

        assertEquals("No User Found", exception.getMessage());
        verify(userRepository).findById("usertest");
    }

    @Test
    void updateUserPartialUpdate() {
        User existingUser = new User();
        existingUser.setUser_id("usertest");
        existingUser.setNickname("oldNickname");
        existingUser.setComment("oldComment");

        User updateUser = new User();
        updateUser.setNickname("newNickname");

        when(userRepository.findById("usertest")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserResponseBody response = userService.update("usertest", updateUser);

        assertEquals("User successfully updated", response.getMessage());
        assertEquals("usertest", response.getUser().getUser_id());
        assertEquals("newNickname", response.getUser().getNickname());
        assertEquals("oldComment", response.getUser().getComment());

        verify(userRepository).findById("usertest");
        verify(userRepository).save(existingUser);
    }
}
