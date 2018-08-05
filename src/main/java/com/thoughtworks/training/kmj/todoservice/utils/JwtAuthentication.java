package com.thoughtworks.training.kmj.todoservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class JwtAuthentication {



    public static String generateToken(Integer id) {

        HashMap<String,Object> claims = new HashMap<>();
        claims.put("id",id);

        //generate
        String token = Jwts.builder()
                .addClaims(claims)
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS512,Constants.secretKey)
                .compact();
        return token;
    }


    public static Claims validateToken(String token) {

        Claims body = Jwts.parser()
                .setSigningKey(Constants.secretKey)
                .parseClaimsJws(token)
                .getBody();

        return body;
    }


    public static Integer getUserId(Claims claims) {
        return Optional.ofNullable(claims.get("id", Integer.class))
                .orElseThrow(() -> new UsernameNotFoundException("can't find user"));
    }
}
