package com.changeside.courseerpbackend.services.user;

import com.changeside.courseerpbackend.models.mybatis.user.User;

public interface UserService {
    void insert(User user);
    User getByEmail(String email);
}
