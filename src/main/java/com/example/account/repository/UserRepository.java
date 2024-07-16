package com.example.account.repository;

import com.example.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByNickname(String nickname);
    @Query("SELECT u FROM User u WHERE u.user_id = :userId")
    Optional<User> findByUserId(@Param("userId") String userId);
}
