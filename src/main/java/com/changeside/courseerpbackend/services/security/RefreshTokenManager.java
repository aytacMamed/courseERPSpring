package com.changeside.courseerpbackend.services.security;

import com.changeside.courseerpbackend.constants.TokenConstants;
import com.changeside.courseerpbackend.models.dto.RefreshTokenDto;
import com.changeside.courseerpbackend.models.mybatis.user.User;
import com.changeside.courseerpbackend.models.proporties.security.SecurityProperties;
import com.changeside.courseerpbackend.services.base.TokenGenerator;
import com.changeside.courseerpbackend.services.base.TokenReader;
import com.changeside.courseerpbackend.services.getters.EmailGetter;
import com.changeside.courseerpbackend.utils.PublicPrivateKeyUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenManager  implements TokenGenerator<RefreshTokenDto>, TokenReader<Claims>, EmailGetter {
    private final SecurityProperties securityProperties;
    @Override
    public String generate(RefreshTokenDto obj) {
        final User user=obj.getUser();
        Claims claims= Jwts.claims();
        claims.put("email",user.getEmail());
        claims.put("type","REFRESH_TOKEN");
        Date now=new Date();
        Date exp=new Date(now.getTime()+securityProperties.getJwt().getRefreshTokenValidityTime(obj.isRememberMe()));

        return Jwts.builder().
                addClaims(claims).
                setExpiration(exp).
                setIssuedAt(now).
                setSubject(String.valueOf(user.getId())).
                signWith(PublicPrivateKeyUtils.getPrivateKey(), SignatureAlgorithm.RS256).
                compact();
    }

    @Override
    public Claims read(String token) {
       Claims tokenData= Jwts.parserBuilder().
                setSigningKey(PublicPrivateKeyUtils.getPublicKey()).
                build().
                parseClaimsJws(token).
                getBody();

       String typeOfToken=tokenData.get("type",String.class);
       if (!typeOfToken.equals("REFRESH_TOKEN")){
           throw new RuntimeException("invalid exception type");
       }
        return tokenData;
    }

    @Override
    public String getEmail(String token) {
        return read(token).get(TokenConstants.EMAIL_KEY,String.class);
    }
}
