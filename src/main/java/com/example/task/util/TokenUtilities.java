package com.example.task.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Component
public class TokenUtilities {

    private final Algorithm algorithm;

    public TokenUtilities() {

        //todo: set secret in environment variable or encrypt and decrypt secret
        this.algorithm = Algorithm.HMAC256(System.getenv("SECRET_KEY").getBytes());
    }


    public String createToken(String username , String requestUrl, Collection<GrantedAuthority> roles , Long expiresAt){

        return JWT
                .create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresAt.intValue() * 60 * 1000))
                .withIssuer(requestUrl)
                .withClaim("roles", roles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }


}
