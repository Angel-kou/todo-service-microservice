package com.thoughtworks.training.kmj;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JwtTest {

    private Map<String,Object> claims;

    @Test
    public void generateJwt() {
        HashMap<String,Object> claims = new HashMap<>();
        claims.put("name","hahha");
        claims.put("role","dev");

        byte[] secretKey = "kitty".getBytes();

        //generate
        String token = Jwts.builder()
                .addClaims(claims)
//                .setExpiration(Date.from(Instant.now().minus(1,ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS512,secretKey)
        .compact();

        //Parse & Verification
        Claims body = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        assertThat(body.get("name"),is("hahha"));
        assertThat(body.get("role"),is("dev"));

        System.out.println(body);

    }
}
