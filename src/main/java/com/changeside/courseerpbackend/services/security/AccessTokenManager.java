package com.changeside.courseerpbackend.services.security;

import com.changeside.courseerpbackend.models.mybatis.user.User;
import com.changeside.courseerpbackend.models.proporties.security.SecurityProperties;
import com.changeside.courseerpbackend.services.base.TokenGenerator;
import com.changeside.courseerpbackend.utils.PublicPrivateKeyUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.util.Date;


@Component
@Slf4j
@RequiredArgsConstructor
public class AccessTokenManager implements TokenGenerator<User>{
private final SecurityProperties securityProperties;

    @Override
    public String generate(User obj) {
        Claims claims= Jwts.claims();
        claims.put("email",obj.getEmail());
        Date now=new Date();
        Date exp=new Date(now.getTime()+securityProperties.getJwt().getAccessTokenValidityTime());

        return Jwts.builder().
                addClaims(claims).
                setExpiration(exp).
                setIssuedAt(now).
                setSubject(String.valueOf(obj.getId())).
                signWith(PublicPrivateKeyUtils.getPrivateKey(), SignatureAlgorithm.RS256).
                compact();
    }
}
