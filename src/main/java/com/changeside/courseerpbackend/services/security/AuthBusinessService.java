package com.changeside.courseerpbackend.services.security;

import com.changeside.courseerpbackend.models.payload.auth.LoginPayload;
import com.changeside.courseerpbackend.models.payload.auth.RefreshTokenPayload;
import com.changeside.courseerpbackend.models.payload.auth.SignUpPayload;
import com.changeside.courseerpbackend.models.response.auth.LoginResponse;

public interface AuthBusinessService {
    LoginResponse login(LoginPayload loginPayload);
    LoginResponse refresh(RefreshTokenPayload refreshTokenPayload);
    void logout();
    void singUp(SignUpPayload payload);
    void setAuthentication(String email);
}
