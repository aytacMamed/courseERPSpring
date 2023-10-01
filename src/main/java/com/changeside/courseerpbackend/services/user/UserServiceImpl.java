package com.changeside.courseerpbackend.services.user;

import com.changeside.courseerpbackend.exception.BaseException;
import com.changeside.courseerpbackend.models.mybatis.user.User;
import com.changeside.courseerpbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void insert(User user) {
        userRepository.insert(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> BaseException.notFound(User.class.getSimpleName(),"email",email)
        );
    }

    @Override
    public boolean checkByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


}
