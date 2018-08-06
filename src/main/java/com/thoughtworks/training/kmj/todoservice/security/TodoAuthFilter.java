package com.thoughtworks.training.kmj.todoservice.security;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.thoughtworks.training.kmj.todoservice.client.UserClient;
import com.thoughtworks.training.kmj.todoservice.dto.User;
import com.thoughtworks.training.kmj.todoservice.utils.JwtAuthentication;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class TodoAuthFilter extends OncePerRequestFilter {

    public static final String KMJ = "kmj";

    @Autowired
    UserClient userClient;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.isEmpty(token)) {
            Claims claims = JwtAuthentication.validateToken(token);
            Integer userId = JwtAuthentication.getUserId(claims);

//            User user = new User(userId,"yaya","123456");

            User user = userClient.verifyTokenIsValid(token);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user,null,
                            ImmutableList.of(new SimpleGrantedAuthority("dev"),
                                    new SimpleGrantedAuthority("play")))
            );

//            RestTemplate restTemplate = new RestTemplateBuilder().build();
//            ResponseEntity<User> responseEntity = restTemplate.postForEntity("http://localhost:8081/api/verification",
//                    token,User.class);
//
//            SecurityContextHolder.getContext().setAuthentication(
//                    new UsernamePasswordAuthenticationToken(responseEntity.getBody(),null,
//                            ImmutableList.of(new SimpleGrantedAuthority("dev"),
//                                    new SimpleGrantedAuthority("play")))
//            );

        }
        filterChain.doFilter(request,response);

    }


    public static User getUser() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return (User) principal;
    }


    public String post(String uri, String json) {
        RestTemplate rest = new RestTemplate();


        HttpEntity<String> requestEntity = new HttpEntity<String>(json, null);
        ResponseEntity<String> responseEntity = rest.exchange("http://localhost:8082" + uri, HttpMethod.POST, requestEntity, String.class);
        System.out.println(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }




}
