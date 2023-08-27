package com.changeside.courseerpbackend.repository;

import com.changeside.courseerpbackend.models.mybatis.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    void insert(User user);
}
