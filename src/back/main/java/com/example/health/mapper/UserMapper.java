package com.example.health.mapper;

import com.example.health.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);
    User findByEmail(@Param("email") String email);
    User findByGoogleId(@Param("googleId") String googleId);
    User findById(@Param("id") Long id);
    int insert(User user);
    int update(User user);
    int updatePassword(@Param("id") Long id, @Param("passwordHash") String passwordHash);
    List<User> findAll();
    int deleteById(@Param("id") Long id);
}


