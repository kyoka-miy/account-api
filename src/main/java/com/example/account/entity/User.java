package com.example.account.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name="users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @NotBlank(message = "required user_id and password")
    @Size(min = 6, max = 20, message = "User id must be between 6 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "User ID must contain only alphanumeric characters")
    private String user_id;
    @NotBlank(message = "required user_id and password")
    @Pattern(regexp = "[\\x21-\\x7E]+", message = "Password must contain only alphanumeric characters and symbols")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Size(max = 30, message = "Nickname must be less than 30 characters")
    private String nickname;
    @Size(max = 100, message = "Nickname must be less than 100 characters")
    private String comment;

    public User() {
    }

    public User(String user_id, String password) {
        this.user_id = user_id;
        this.password = password;
    }

    public User(String user_id, String password, String nickname, String comment) {
        this.user_id = user_id;
        this.password = password;
        this.nickname = nickname;
        this.comment = comment;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
