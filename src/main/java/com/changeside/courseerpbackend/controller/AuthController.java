package com.changeside.courseerpbackend.controller;

import com.changeside.courseerpbackend.models.base.BaseResponse;
import com.changeside.courseerpbackend.models.payload.auth.LoginPayload;
import com.changeside.courseerpbackend.models.payload.auth.RefreshTokenPayload;
import com.changeside.courseerpbackend.models.payload.auth.SignUpPayload;
import com.changeside.courseerpbackend.models.payload.otp.BaseOTPChannelRequest;
import com.changeside.courseerpbackend.models.payload.otp.BaseOTPRequest;
import com.changeside.courseerpbackend.models.response.auth.LoginResponse;
import com.changeside.courseerpbackend.services.security.AuthBusinessService;
import lombok.RequiredArgsConstructor;
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
    public BaseResponse<LoginResponse> login(@RequestBody LoginPayload loginPayload) {
        return BaseResponse.success(authBusinessService.login(loginPayload));
    }

    ;

    @PostMapping("/token/refresh")
    public BaseResponse<LoginResponse> refresh(@RequestBody RefreshTokenPayload refreshTokenPayload) {
        return BaseResponse.success(authBusinessService.refresh(refreshTokenPayload));
    }

    @PostMapping("/logout")
    public BaseResponse<Void> logout() {
        authBusinessService.logout();
        return BaseResponse.success();
    }

    @PostMapping("/sing-up")
    public BaseResponse<Void> singUp(@RequestBody SignUpPayload payload) {
//        System.out.println(UserEntityMapper.INSTANCE.fromSignUpPayloadToUser(payload, "123123", 1L));

        authBusinessService.singUp(payload);
        return BaseResponse.success();
    }

    @PostMapping("/sign-up/otp/request")
    public BaseResponse<Void> otpRequest(@RequestBody BaseOTPChannelRequest payload){
        authBusinessService.singUpOTP(payload);
        return BaseResponse.success();

    }

    @PostMapping("/sign-up/otp/confirmation")
    public BaseResponse<Void> otpConfirmation(@RequestBody BaseOTPRequest payload){
        authBusinessService.singUpOTPConfirmation(payload);
        return BaseResponse.success();

    }

}
