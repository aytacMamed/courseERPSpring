package com.changeside.courseerpbackend.controller;

import com.changeside.courseerpbackend.models.base.BaseResponse;
import com.changeside.courseerpbackend.models.dto.RefreshTokenDto;
import com.changeside.courseerpbackend.models.mybatis.user.User;
import com.changeside.courseerpbackend.models.payload.auth.LoginPayload;
import com.changeside.courseerpbackend.models.payload.auth.RefreshTokenPayload;
import com.changeside.courseerpbackend.models.response.auth.LoginResponse;
import com.changeside.courseerpbackend.services.security.AccessTokenManager;
import com.changeside.courseerpbackend.services.security.AuthBusinessService;
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
private final AuthBusinessService authBusinessService;

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginPayload loginPayload){
       return BaseResponse.success(authBusinessService.login(loginPayload));
    };

    @PostMapping("/token/refresh")
    public BaseResponse<LoginResponse> refresh(@RequestBody RefreshTokenPayload refreshTokenPayload){
        return BaseResponse.success(authBusinessService.refresh(refreshTokenPayload)) ;
    }

    @PostMapping("/logout")
    public BaseResponse<Void> logout(){
        authBusinessService.logout();
        return BaseResponse.success();
    }
}
