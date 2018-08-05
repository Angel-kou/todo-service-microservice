package com.thoughtworks.training.kmj.todoservice.security;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.thoughtworks.training.kmj.todoservice.service.UserService;
import com.thoughtworks.training.kmj.todoservice.utils.JwtAuthentication;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


@Component
public class TodoAuthFilter extends OncePerRequestFilter {

    public static final String KMJ = "kmj";

    @Autowired
    private UserService userService;

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        System.out.println("path:  "+request.getContextPath());
//        return request.getContextPath().startsWith("/login");
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(!StringUtils.isEmpty(token)){
            Claims claims = JwtAuthentication.validateToken(token);
            Integer userId = JwtAuthentication.getUserId(claims);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userId,null,
                            ImmutableList.of(new SimpleGrantedAuthority("dev"),
                                    new SimpleGrantedAuthority("play")))
            );

        }
        filterChain.doFilter(request,response);

    }


    public static Integer getUserId() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return (Integer) principal;
    }




}
