package com.changeside.courseerpbackend.repository;

import com.changeside.courseerpbackend.models.mybatis.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserRepository {
    void insert(User user);
    Optional<User> findByEmail(@Param("email") String email);
}
