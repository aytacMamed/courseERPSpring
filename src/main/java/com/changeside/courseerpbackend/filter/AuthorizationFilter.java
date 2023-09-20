package com.changeside.courseerpbackend.filter;

import com.changeside.courseerpbackend.constants.TokenConstants;
import com.changeside.courseerpbackend.models.mybatis.user.User;
import com.changeside.courseerpbackend.models.security.LoggedInUserDetails;
import com.changeside.courseerpbackend.services.security.AccessTokenManager;
import com.changeside.courseerpbackend.services.security.AuthBusinessService;
import com.changeside.courseerpbackend.services.user.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final AccessTokenManager accessTokenManager;
    private final UserDetailsService userDetailsService;
    private final AuthBusinessService authBusinessService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(token) && token.startsWith(TokenConstants.PREFIX)) {
            final String accessToken = token.substring(7);
           authBusinessService.setAuthentication(accessTokenManager.getEmail(accessToken));
        }
        System.out.println(token);
        filterChain.doFilter(request, response);

    }
}
