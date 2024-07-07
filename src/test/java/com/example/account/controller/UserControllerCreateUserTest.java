package com.example.account.controller;

import com.example.account.entity.User;
import com.example.account.response.UserResponseBody;
import com.example.account.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerCreateUserTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = CustomObjectMapper.createCustomObjectMapper();
    @MockBean
    private UserService userService;

    private MockHttpServletRequestBuilder createRequest(User user) throws Exception {
        return MockMvcRequestBuilders.post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user));
    }

    @Test
    void createUserSuccess() throws Exception {
        User requestUser = new User();
        requestUser.setUser_id("usertest");
        requestUser.setPassword("password");

        UserResponseBody mockResponse = new UserResponseBody("Account successfully created", requestUser);
        when(userService.add(any())).thenReturn(mockResponse);

        ResultActions response = mockMvc.perform(createRequest(requestUser));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Account successfully created"))
                .andExpect(jsonPath("$.user.user_id").value("usertest"))
                .andExpect(jsonPath("$.user.nickname").value("usertest"));
    }

    @Test
    void createUserPasswordMissing() throws Exception {
        User requestUser = new User();
        requestUser.setUser_id("usertest");

        ResultActions response = mockMvc.perform(createRequest(requestUser));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Account creation failed"))
                .andExpect(jsonPath("$.cause").value("required user_id and password"));
    }

    @Test
    void createUserPasswordTooShort() throws Exception {
        User requestUser = new User();
        requestUser.setUser_id("usertest");
        requestUser.setPassword("short");

        ResultActions response = mockMvc.perform(createRequest(requestUser));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Account creation failed"))
                .andExpect(jsonPath("$.cause").value("Password must be between 8 and 20 characters"));
    }

    @Test
    void createUserUserIdBannedFormat() throws Exception {
        User requestUser = new User();
        requestUser.setUser_id("usertest&&");
        requestUser.setPassword("password");

        ResultActions response = mockMvc.perform(createRequest(requestUser));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Account creation failed"))
                .andExpect(jsonPath("$.cause").value("User ID must contain only alphanumeric characters"));
    }
}
