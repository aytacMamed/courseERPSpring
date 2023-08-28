package com.changeside.courseerpbackend;

import com.changeside.courseerpbackend.models.enums.user.UserStatus;
import com.changeside.courseerpbackend.models.mybatis.user.User;
import com.changeside.courseerpbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseErpBackendApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CourseErpBackendApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
//        User user=User.builder().name("Aytac").
//                surname("Mammadli").
//                status(UserStatus.ACTIVE).
//                roleId(1L).
//                email("email@email.com").
//                phoneNumber("5555555555").
//                password("123456").build();
//        userRepository.insert(user);

    }
}
