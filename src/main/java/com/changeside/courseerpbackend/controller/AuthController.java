package com.changeside.courseerpbackend.controller;

import com.changeside.courseerpbackend.models.base.BaseResponse;
import com.changeside.courseerpbackend.models.dto.RefreshTokenDto;
import com.changeside.courseerpbackend.models.mybatis.user.User;
import com.changeside.courseerpbackend.models.payload.auth.LoginPayload;
import com.changeside.courseerpbackend.models.response.auth.LoginResponse;
import com.changeside.courseerpbackend.services.security.AccessTokenManager;
import com.changeside.courseerpbackend.services.security.RefreshTokenManager;
import com.changeside.courseerpbackend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager refreshTokenManager;
    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginPayload loginPayload){
        authenticate(loginPayload);
        User user=userService.getByEmail(loginPayload.getEmail());
        return BaseResponse.success(LoginResponse.builder().
                accessToken(accessTokenManager.generate(user)).
                refreshToken(refreshTokenManager.generate(RefreshTokenDto.
                                builder().
                                user(user).
                                rememberMe(loginPayload.isRememberMe()).
                                build())).
                build());
    };

    // temp

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    private void authenticate(LoginPayload request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
            );
        }catch (AuthenticationException e){
            throw new RuntimeException("Exception");
        }
    }
}
